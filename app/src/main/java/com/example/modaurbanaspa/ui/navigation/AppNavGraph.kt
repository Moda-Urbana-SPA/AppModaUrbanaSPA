package com.example.modaurbanaspa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.NavDestination
import com.example.modaurbanaspa.ui.screens.*

object Routes {
    const val HOME="home"; const val CATALOG="catalog"; const val CART="cart"; const val PROFILE="profile"
    const val DETAIL="detail"; const val CHECKOUT="checkout"; const val ORDER_RESULT="order_result"
}

private data class BottomItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
private fun BottomBar(navController: NavHostController){
    val items = listOf(
        BottomItem(Routes.HOME, "Inicio", Icons.Filled.Home),
        BottomItem(Routes.CATALOG, "Catálogo", Icons.Filled.ListAlt),
        BottomItem(Routes.CART, "Carrito", Icons.Filled.ShoppingCart),
        BottomItem(Routes.PROFILE, "Perfil", Icons.Filled.Person),
    )
    NavigationBar {
        val current = navController.currentBackStackEntryAsState().value?.destination
        items.forEach { itx ->
            val selected = isRouteInHierarchy(current, itx.route)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(itx.route){
                        popUpTo(navController.graph.startDestinationId){ saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(itx.icon, contentDescription = itx.label) },
                label = { Text(itx.label) }
            )
        }
    }
}

private fun isRouteInHierarchy(destination: NavDestination?, route: String): Boolean {
    var current = destination
    while (current != null) {
        if (current.route == route) return true
        current = current.parent
    }
    return false
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()){
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ){ inner ->
        NavHost(navController, startDestination = Routes.HOME, modifier = Modifier.padding(inner)) {
            composable(Routes.HOME){ HomeScreen() }
            composable(Routes.CATALOG){ CatalogScreen(navController) }
            composable("${Routes.DETAIL}/{id}"){ back ->
                back.arguments?.getString("id")?.toIntOrNull()?.let { ProductDetailScreen(navController, it) }
            }
            composable(Routes.CART){ CartScreen(navController) }
            composable(Routes.CHECKOUT){ CheckoutScreen(navController) }
            composable(Routes.PROFILE){ ProfileScreen() }
            composable("${Routes.ORDER_RESULT}/{orderId}"){ back ->
                OrderResultScreen(back.arguments?.getString("orderId") ?: "-", onClose = {
                    navController.navigate(Routes.CATALOG){ popUpTo(0) }
                })
            }
        }
    }
}
