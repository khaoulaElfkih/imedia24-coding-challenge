package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.CreateProductDto
import de.imedia24.shop.domain.product.ProductDto
import de.imedia24.shop.domain.product.UpdateProductDto
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json"])
    fun findProductBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductDto> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok().body(product)
        }
    }

    @GetMapping("/products", produces = ["application/json"])
    fun findProductsBySku(
            @RequestParam(name = "skus") skus: String
    ): ResponseEntity<List<ProductDto>> {
        logger.info("Retrieving products")
        val listSkus = skus.split(",");
        val products = productService.findProductsBySku(listSkus)
        return if(products.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }
    @PostMapping("/products", produces = ["application/json"])
    fun addNewProduct(@RequestBody product: CreateProductDto): ResponseEntity<ProductDto> {
        logger.info("Request to add a product")
        val persistedProduct = productService.addProduct(product);
        if (ObjectUtils.isEmpty(persistedProduct)) {
            return ResponseEntity<ProductDto>(HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(persistedProduct, HttpStatus.CREATED)
    }

    @PutMapping("/products/{sku}", produces = ["application/json"])
    fun updateProduct(@PathVariable("sku") sku: String, @RequestBody product: UpdateProductDto) : ResponseEntity<ProductDto> {
        logger.info("Request to update product: {}", sku)
        val updatedProduct = productService.updateProduct(sku, product)
        return if (updatedProduct != null) {
            ResponseEntity.ok().body(updatedProduct)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
