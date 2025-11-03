package com.example.modaurbanaspa.model

data class Carrito(
    val items: List<CartItem> = emptyList()
){
    val total: Int get() = items.sumOf { it.product.price * it.qty }
}
