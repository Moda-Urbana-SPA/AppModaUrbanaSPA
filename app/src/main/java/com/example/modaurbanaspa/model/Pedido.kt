package com.example.modaurbanaspa.model


data class Pedido(
    val id: String,
    val nombreCliente: String,
    val correo: String,
    val telefono: String?,
    val direccion: String,
    val comuna: String,
    val detalle: List<ProductoDetalle>,
){
    val total: Int get() = detalle.sumOf { it.lineTotal }
}
