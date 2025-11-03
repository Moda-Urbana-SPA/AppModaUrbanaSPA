package com.example.modaurbanaspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modaurbanaspa.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CartScreen(navController: NavController, cartVm: CartViewModel = viewModel()){
    val state by cartVm.state.collectAsState()
    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text("Carrito", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(state.items){ it ->
                ElevatedCard(Modifier.fillMaxWidth()){
                    Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Column(Modifier.weight(1f)){
                            Text(it.product.name)
                            Text("Talla: ${it.size ?: "-"}")
                        }
                        Text("x${it.qty}")
                        Text("$${it.product.price * it.qty}")
                        TextButton(onClick = { cartVm.remove(it.product.id, it.size) }){ Text("Quitar") }
                    }
                }
            }
        }
        Text("Total: $${state.total}", style = MaterialTheme.typography.titleLarge)
        Button(onClick = { navController.navigate("checkout") }, enabled = state.items.isNotEmpty(), modifier = Modifier.fillMaxWidth()){ Text("Continuar") }
    }
}