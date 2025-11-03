package com.example.modaurbanaspa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modaurbanaspa.viewmodel.CartViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.random.Random

@Composable
fun CheckoutScreen(navController: NavController, cartVm: CartViewModel = viewModel()){
    val state by cartVm.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val isValid = name.isNotBlank() && email.contains("@") && address.isNotBlank() && state.items.isNotEmpty()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)){
        Text("Datos de entrega", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(name, {name=it}, label={Text("Nombre")}, modifier=Modifier.fillMaxWidth())
        OutlinedTextField(email, {email=it}, label={Text("Email")}, modifier=Modifier.fillMaxWidth())
        OutlinedTextField(phone, {phone=it}, label={Text("Teléfono")}, modifier=Modifier.fillMaxWidth())
        OutlinedTextField(address, {address=it}, label={Text("Dirección")}, modifier=Modifier.fillMaxWidth())
        OutlinedTextField(notes, {notes=it}, label={Text("Notas")}, modifier=Modifier.fillMaxWidth())
        Button(onClick={
            val orderId = "MU-"+Random.nextInt(100000,999999)
            cartVm.clear()
            navController.navigate("order_result/$orderId")
        }, enabled=isValid, modifier=Modifier.fillMaxWidth()){ Text("Confirmar pedido ($${state.total})") }
    }
}