package com.example.modaurbanaspa.viewmodel
import androidx.lifecycle.ViewModel
import com.example.modaurbanaspa.model.CartItem
import com.example.modaurbanaspa.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartState(val items: List<CartItem> = emptyList()){
    val total: Int get() = items.sumOf { it.product.price * it.qty }
}

class CartViewModel: ViewModel() {
    private val _state = MutableStateFlow(CartState())
    val state: StateFlow<CartState> = _state.asStateFlow()

    fun add(product: Product, size: String?) {
        val current = _state.value.items.toMutableList()
        val idx = current.indexOfFirst { it.product.id==product.id && it.size==size }
        if(idx>=0){
            val it = current[idx]
            current[idx] = it.copy(qty = it.qty + 1)
        }else current.add(CartItem(product = product, size = size, qty = 1))
        _state.value = CartState(current)
    }
    fun remove(productId:Int, size:String?){
        _state.value = CartState(_state.value.items.filterNot { it.product.id==productId && it.size==size })
    }
    fun clear(){ _state.value = CartState() }
}