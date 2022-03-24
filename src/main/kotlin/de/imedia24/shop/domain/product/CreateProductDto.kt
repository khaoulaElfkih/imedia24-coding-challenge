package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

data class CreateProductDto(
        val sku: String,
        val name: String,
        val description: String,
        val price: BigDecimal,
        val stock: Int
)