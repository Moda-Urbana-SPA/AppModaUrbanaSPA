package com.example.modaurbanaspa.model
data class Variant(val size: String, val color: String, val stock: Int)
data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val category: String,
    val imageUrl: String? = null,
    val variants: List<Variant> = emptyList(),
    val description: String? = null
)