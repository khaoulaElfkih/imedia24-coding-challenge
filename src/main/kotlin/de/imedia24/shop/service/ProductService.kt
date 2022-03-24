package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.CreateProductDto
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import de.imedia24.shop.domain.product.UpdateProductDto
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

    fun addProduct(product: CreateProductDto): ProductResponse = productRepository.save(ProductEntity.fromDto(product)).toProductResponse()

    fun updateProduct(sku: String, product: UpdateProductDto): ProductResponse? {

        val currentProduct = productRepository.findBySku(sku)
        return if (currentProduct != null) productRepository.save(ProductEntity.fromDto(product, currentProduct)).toProductResponse()
        else null
    }
}
