package com.example.modaurbanaspa.model
data class Order(val id: String, val items: List<CartItem>, val customer: Customer, val total: Int)