package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if(product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun findProductsBySku(
            @RequestParam(name = "skus") skus: String
    ): ResponseEntity<List<ProductResponse>> {
        val listSkus = skus.split(",");
        val products = productService.findProductsBySku(listSkus)
        return if(products.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }
    /*@PostMapping("/products", produces = ["application/json;charset=utf-8"])
    fun addNewProduct(@RequestBody product: ProductEntity): ResponseEntity<ProductRequest> {
        val persistedProduct = productService.save(product);
        /*if (ObjectUtils.isEmpty(persistedGadget)) {
            return ResponseEntity<Gadget>(HttpStatus.BAD_REQUEST)
        }
        val headers = HttpHeaders()
        headers.setLocation(uri.path("/gadget/{gadgetId}").buildAndExpand(gadget.gadgetId).toUri());*/
        return ResponseEntity(persistedProduct,HttpStatus.CREATED)
    }
    @GetMapping("/hello", produces = ["application/json;charset=utf-8"])
    fun muTest():String ="this my new kotlin project";*/

}
