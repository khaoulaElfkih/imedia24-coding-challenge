package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? = productRepository.findBySku(sku)?.toProductResponse()

    fun findProductsBySku(skus: List<String>?): List<ProductResponse> {
        val products = mutableListOf<ProductResponse>()
        skus?.forEach{
            productRepository.findBySku(it)?.let { it1 -> products.add(it1.toProductResponse()) }
        }
        return products;
    }


    /*fun findAllProducts(): List<ProductResponse>? {
        val products = mutableListOf<ProductResponse>()
         productRepository.findAll().forEach {
             products.add(it.toProductResponse())
        }
        return products
    }

    fun addProduct(product: ProductEntity): ProductRequest =productRepository.save(product).toProductRequest();*/

}
