package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = [
            "sprint.datasource.url=jdbc:h2:mem:testdb"
        ]
)
class ProductControllerItTest(@Autowired val client: TestRestTemplate ) {

    @Test
    fun `Retrieving a product by its sku should result in status 200 OK`() {

        val productResponse : ResponseEntity<ProductDto> = client.getForEntity("http://localhost:8080/products/{sku}",
                ProductDto::class.java,12)
        //Then
        assertThat(productResponse).isNotNull
        assertThat(productResponse.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(productResponse.body).isNotNull
        assertThat(productResponse.body?.sku).isEqualTo("12")
        assertThat(productResponse.body?.description).isEqualTo("DESCRIPTION")
        assertThat(productResponse.body?.price).isEqualTo(BigDecimal(23))
        assertThat(productResponse.body?.stock).isEqualTo(20)
    }

    @Test
    fun `Retrieving an unexisting product by sku should result in status 404 Not Found`() {

        val productResponse : ResponseEntity<ProductDto> = client.getForEntity("http://localhost:8080/products/{sku}",
                ProductDto::class.java,88)

        assertThat(productResponse).isNotNull
        assertThat(productResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(productResponse.body).isNull()
    }
    @Test
    fun `Retrieving a list of products by their skus should result in status 200 OK`() {

        val productsResponse : ResponseEntity<List<ProductDto>> = client.exchange("http://localhost:8080/products?skus={skus}",
                HttpMethod.GET, HttpEntity.EMPTY, object : ParameterizedTypeReference<List<ProductDto>>(){}, "12,15")

        assertThat(productsResponse).isNotNull
        assertThat(productsResponse.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(productsResponse.body).isNotNull
        /*assertThat(productsResponse.body?.get(0)?.sku).isEqualTo("12")
        assertThat(productsResponse.body?.get(0)?.description).isEqualTo("DESCRIPTION")
        assertThat(productsResponse.body?.get(0)?.price).isEqualTo(BigDecimal(23))
        assertThat(productsResponse.body?.get(0)?.stock).isEqualTo(20)*/
    }

    @Test
    fun `Retrieving a list of products by unexisting skus should result in status 404 Not found`() {

        val productsResponse : ResponseEntity<List<ProductDto>> = client.exchange("http://localhost:8080/products?skus={skus}",
                HttpMethod.GET, HttpEntity.EMPTY, object : ParameterizedTypeReference<List<ProductDto>>(){}, "5678,34689")

        assertThat(productsResponse).isNotNull
        assertThat(productsResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(productsResponse.body).isNull()
    }

}