package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductDto
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import org.mockito.BDDMockito.given
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*



@WebMvcTest
class ProductControllerUtTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    lateinit var productService: ProductService


    @Test
    fun `Retrieving a product by its sku should result in status 200 OK`() {
        //Given
        val product = ProductDto("SKU34-45", "Ristretto", "Ristretto is a short shot of a more highly concentrated espresso coffee.", BigDecimal(60), 300)

        given(productService.findProductBySku("SKU34-45")).willReturn(product)

        //When and Then
        mockMvc.perform(get("/products/{sku}", "SKU34-45")
                .contentType(MediaType.APPLICATION_JSON_VALUE ))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Ristretto"))
                .andExpect(jsonPath("$.price").value(BigDecimal(60)))
                .andExpect(jsonPath("$.stock").value(300))
    }

    @Test
    fun `Retrieving an unexisting product by sku should result in status 404 Not Found`() {
        //Given
        given(productService.findProductBySku("SKU56-77")).willReturn(null)

        //When and Then
        mockMvc.perform(get("/products/{sku}", "SKU34-45")
                .contentType(MediaType.APPLICATION_JSON_VALUE ))
                .andDo(print())
                .andExpect(status().isNotFound)

    }

    @Test
    fun `Retrieving a list of products by their skus should result in status 200 OK`() {
        //Given
        val ristretto = ProductDto("SKU34-45", "Ristretto", "Ristretto is a short shot of a more highly concentrated espresso coffee.", BigDecimal(60), 300)
        val fortissio = ProductDto("SKU34-46", "Fortissio", "Fortissio is a short shot of a more highly concentrated espresso coffee.", BigDecimal(100), 500)
        val envivo  = ProductDto("SKU34-47", "Envivo", "Envivo is a short shot of a more highly concentrated espresso coffee.", BigDecimal(90), 400)

        given(productService.findProductsBySku(listOf("SKU34-45","SKU34-46","SKU34-47"))).willReturn(listOf(ristretto,fortissio,envivo))

        //When and Then
        mockMvc.perform(get("/products?skus={skus}", "SKU34-45,SKU34-46,SKU34-47")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].sku").value("SKU34-45"))
                .andExpect(jsonPath("$[0].name").value("Ristretto"))
                .andExpect(jsonPath("$[0].description").value("Ristretto is a short shot of a more highly concentrated espresso coffee."))
                .andExpect(jsonPath("$[0].price").value(BigDecimal(60)))
                .andExpect(jsonPath("$[0].stock").value(300))
                .andExpect(jsonPath("$[1].sku").value("SKU34-46"))
                .andExpect(jsonPath("$[1].name").value("Fortissio"))
                .andExpect(jsonPath("$[1].description").value("Fortissio is a short shot of a more highly concentrated espresso coffee."))
                .andExpect(jsonPath("$[1].price").value( BigDecimal(100)))
                .andExpect(jsonPath("$[1].stock").value(500))
                .andExpect(jsonPath("$[2].sku").value("SKU34-47"))
                .andExpect(jsonPath("$[2].name").value("Envivo"))
                .andExpect(jsonPath("$[2].description").value("Envivo is a short shot of a more highly concentrated espresso coffee."))
                .andExpect(jsonPath("$[2].price").value( BigDecimal(90)))
                .andExpect(jsonPath("$[2].stock").value(400))
    }

    @Test
    fun `Retrieving a list of products by unexisting skus should result in status 404 Not found`() {

        //Given
        given(productService.findProductsBySku(listOf("SKU34-45","SKU34-46","SKU34-47"))).willReturn(listOf())

        //When and Then
        mockMvc.perform(get("/products?skus={skus}", "SKU34-45,SKU34-46,SKU34-47")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound)
    }
    @Test
    fun `Retrieving a list of products by an empty skus param should result in status 400 bad request`() {

        //When and Then
        mockMvc.perform(get("/products?skus=", "")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest)
    }
}