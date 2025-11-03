@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.modaurbanaspa.ui.screens
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modaurbanaspa.repository.LocalCatalogRepository
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext

@Composable
fun CatalogScreen(navController: NavController){
    val ctx = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var list by remember { mutableStateOf(listOf<com.example.modaurbanaspa.model.Product>()) }
    var query by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit){
        val repo = LocalCatalogRepository(ctx)
        list = repo.getAll()
        isLoading = false
    }

    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
        OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Buscar producto") })
        // Filtros simples: categoría y talla
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
            var expandC by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expandC, onExpandedChange = { expandC = !expandC }){
                OutlinedTextField(
                    value = selectedCategory ?: "Categoría",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f).menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandC, onDismissRequest = { expandC = false }){
                    (list.map { it.category }.distinct() + "Todas").forEach { c ->
                        DropdownMenuItem(text = { Text(c) }, onClick = {
                            selectedCategory = if (c=="Todas") null else c; expandC=false
                        })
                    }
                }
            }

            var expandS by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expandS, onExpandedChange = { expandS = !expandS }){
                OutlinedTextField(
                    value = selectedSize ?: "Talla",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.weight(1f).menuAnchor()
                )
                ExposedDropdownMenu(expanded = expandS, onDismissRequest = { expandS = false }){
                    (list.flatMap { it.variants.map { v -> v.size } }.distinct() + "Todas").forEach { s ->
                        DropdownMenuItem(text = { Text(s) }, onClick = {
                            selectedSize = if (s=="Todas") null else s; expandS=false
                        })
                    }
                }
            }
        }

        if(isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        else LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)){
            val filtered = list.filter { p ->
                (query.isBlank() || p.name.contains(query, true)) &&
                (selectedCategory==null || p.category.equals(selectedCategory, true)) &&
                (selectedSize==null || p.variants.any{ it.size==selectedSize })
            }
            items(filtered){ p ->
                ElevatedCard(Modifier.fillMaxWidth().clickable { navController.navigate("detail/${p.id}") }){
                    Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)){
                        AsyncImage(model = p.imageUrl, contentDescription = p.name, modifier = Modifier.size(84.dp))
                        Column(Modifier.weight(1f)){
                            Text(p.name, style = MaterialTheme.typography.titleMedium)
                            Text("$${p.price}", color = MaterialTheme.colorScheme.primary)
                            Text("${p.category} • Tallas: "+ p.variants.map{it.size}.distinct().joinToString(", "))
                        }
                    }
                }
            }
        }
    }
}