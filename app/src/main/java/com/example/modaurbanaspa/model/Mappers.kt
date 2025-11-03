package com.example.modaurbanaspa.model


fun List<CartItem>.toCarrito(): Carrito = Carrito(this)

fun CartItem.toProductoDetalle(): ProductoDetalle = ProductoDetalle(
    productId = product.id,
    name = product.name,
    unitPrice = product.price,
    size = size,
    color = color,
    quantity = qty
)

fun Carrito.toPedidoBasico(
    id: String,
    nombreCliente: String,
    correo: String,
    telefono: String?,
    direccion: String,
    comuna: String
): Pedido = Pedido(
    id = id,
    nombreCliente = nombreCliente,
    correo = correo,
    telefono = telefono,
    direccion = direccion,
    comuna = comuna,
    detalle = items.map { it.toProductoDetalle() }
)
