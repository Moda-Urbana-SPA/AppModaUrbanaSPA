package com.example.modaurbanaspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderResultScreen(orderId:String, onClose:()->Unit){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)){
            Text("¡Pedido confirmado!", style = MaterialTheme.typography.headlineSmall)
            Text("Código: $orderId")
            Button(onClick = onClose){ Text("Volver al catálogo") }
        }
    }
}