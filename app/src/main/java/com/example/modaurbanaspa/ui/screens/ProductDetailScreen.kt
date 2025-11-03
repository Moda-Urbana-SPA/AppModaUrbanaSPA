@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.modaurbanaspa.ui.screens
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modaurbanaspa.repository.LocalCatalogRepository
import androidx.compose.ui.platform.LocalContext
import com.example.modaurbanaspa.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetailScreen(navController: NavController, productId: Int, cartVm: CartViewModel = viewModel()){
    val ctx = LocalContext.current
    var p by remember { mutableStateOf<com.example.modaurbanaspa.model.Product?>(null) }
    var size by remember { mutableStateOf<String?>(null) }
    var qty by remember { mutableStateOf(1) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(productId){
        val repo = LocalCatalogRepository(ctx)
        p = repo.getById(productId)
        size = p?.variants?.firstOrNull()?.size
        loading = false
    }

    if(loading) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){ CircularProgressIndicator() }
    else p?.let { prod ->
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)){
            AsyncImage(model = prod.imageUrl, contentDescription = prod.name, modifier = Modifier.fillMaxWidth().height(220.dp))
            Text(prod.name, style = MaterialTheme.typography.headlineSmall)
            Text("$${prod.price}", color = MaterialTheme.colorScheme.primary)

            Text("Talla", style = MaterialTheme.typography.labelLarge)
            var expand by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expand, onExpandedChange = { expand = !expand }){
                OutlinedTextField(value = size ?: "Seleccionar", onValueChange = {}, readOnly = true, modifier = Modifier.menuAnchor())
                ExposedDropdownMenu(expanded = expand, onDismissRequest = { expand = false }){
                    prod.variants.map { it.size }.distinct().forEach { s ->
                        DropdownMenuItem(text = { Text(s) }, onClick = { size = s; expand = false })
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)){
                OutlinedButton({ if(qty>1) qty-- }){ Text("-") }
                Text("$qty")
                OutlinedButton({ qty++ }){ Text("+") }
            }

            Button(onClick = {
                repeat(qty){ cartVm.add(prod, size) }
                navController.popBackStack()
            }, enabled = size != null, modifier = Modifier.fillMaxWidth()){
                Text("Agregar al carrito")
            }
        }
    } ?: Text("Producto no encontrado", modifier = Modifier.padding(16.dp))
}