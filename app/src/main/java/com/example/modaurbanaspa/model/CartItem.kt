package com.example.modaurbanaspa.model
data class CartItem(
    val product: Product,
    val size: String? = null,
    val color: String? = null,
    val qty: Int = 1
)
