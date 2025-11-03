package com.example.modaurbanaspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)){
            Text("Bienvenido a Moda Urbana", style = MaterialTheme.typography.headlineSmall)
            Text("Explora el catálogo usando la barra inferior.")
        }
    }
}
