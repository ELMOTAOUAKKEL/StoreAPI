package de.imedia24.shop.domain.product

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class ProductRequest(
    @NotEmpty
    @Size(min = 3)
    val sku: String,
    val name: String,
    @Positive
    val price: BigDecimal,
    val description: String?,
)
