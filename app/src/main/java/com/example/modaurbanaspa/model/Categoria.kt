package com.example.modaurbanaspa.model


enum class CategoryType { PANTALON, POLERON, POLERA, ACCESORIOS, CALZADO }

data class Categoria(
    val type: CategoryType,
    val sizes: List<String> = emptyList() // Ej: ["XS","S","M","L","XL"]
)
