package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.CreateProductDto
import de.imedia24.shop.domain.product.ProductDto
import de.imedia24.shop.domain.product.UpdateProductDto
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductDto? = productRepository.findBySku(sku)?.toDto()

    fun findProductsBySku(skus: List<String>?): List<ProductDto> {
        val products = mutableListOf<ProductDto>()
        skus?.forEach{
            productRepository.findBySku(it)?.let { product -> products.add(product.toDto()) }
        }
        return products;
    }

    fun addProduct(product: CreateProductDto): ProductDto = productRepository.save(ProductEntity.fromDto(product)).toDto()

    fun updateProduct(sku: String, product: UpdateProductDto): ProductDto? {

        val currentProduct = productRepository.findBySku(sku)
        return if (currentProduct != null) productRepository.save(ProductEntity.fromDto(product, currentProduct)).toDto()
        else null
    }
}
