package com.example.modaurbanaspa.model


data class ProductoDetalle(
    val productId: Int,
    val name: String,
    val unitPrice: Int,
    val size: String? = null,
    val color: String? = null,
    val quantity: Int = 1
){
    val lineTotal: Int get() = unitPrice * quantity
}
