package com.example.modaurbanaspa.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modaurbanaspa.viewmodel.CartViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen() {
    val cartVm: CartViewModel = viewModel()
    val items = cartVm.state.value.items
    val total = items.sumOf { it.product.price * it.qty }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JP",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Text("José Pablo Díaz López", style = MaterialTheme.typography.titleLarge)
            Text("Cliente", color = Color.Gray)

            Divider(Modifier.padding(vertical = 8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📧 jose@gmail.com")
                Text("📞 +56 9 9303 1485")
                Text("📍 Las Condes, Santiago")
            }

            Divider(Modifier.padding(vertical = 8.dp))

            Text("🛒 Resumen de compras", style = MaterialTheme.typography.titleMedium)
            Text("Productos en carrito: ${items.size}")
            Text("Total estimado: $${total}")

            Divider(Modifier.padding(vertical = 8.dp))

            Button(
                onClick = { /* TODO: acción futura de editar perfil */ },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Editar perfil", color = Color.White)
            }

            OutlinedButton(onClick = { /* TODO: cerrar sesión */ }) {
                Text("Cerrar sesión")
            }
        }
    }
}
