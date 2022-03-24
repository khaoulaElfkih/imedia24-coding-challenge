package de.imedia24.shop.db.entity

import de.imedia24.shop.domain.product.CreateProductDto
import de.imedia24.shop.domain.product.UpdateProductDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @Column(name = "sku", nullable = false)
    val sku: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @Column(name = "stock", nullable = false)
    val stock: Int,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime = ZonedDateTime.now()
){
    companion object {
        fun fromDto(dto: CreateProductDto) = ProductEntity(
                sku = dto.sku,
                name = dto.name,
                description = dto.description ?: "",
                price = dto.price,
                stock = dto.stock)

        fun fromDto(dto: UpdateProductDto, existingProduct: ProductEntity) = ProductEntity(
                sku = existingProduct.sku,
                name = dto.name ?: existingProduct.name,
                description = dto.description ?: existingProduct.name,
                price = dto.price ?: existingProduct.price,
                stock = existingProduct.stock,
                updatedAt = ZonedDateTime.now(),
                createdAt = existingProduct.createdAt)
    }

}
