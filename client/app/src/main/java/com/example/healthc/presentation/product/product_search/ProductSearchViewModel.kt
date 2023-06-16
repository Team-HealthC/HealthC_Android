package com.example.healthc.presentation.product.product_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.repository.ProductRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _productIdEvent = MutableStateFlow<ProductIdUiEvent>(
        ProductIdUiEvent.Unit
    )
    val productIdEvent : StateFlow<ProductIdUiEvent>
        get() = _productIdEvent

    val product : MutableStateFlow<String> = MutableStateFlow<String>("")
    val notFounded : MutableStateFlow<String> = MutableStateFlow<String>("")

    fun getProductIds() : Boolean{
        if(product.value.isBlank()){
            _productIdEvent.value = ProductIdUiEvent.NotFounded
            return false
        }
        viewModelScope.launch{
            _productIdEvent.value = ProductIdUiEvent.Loading
            val result = repository.getProductIds(product.value)
            when(result){
                is Resource.Success -> {
                    if(result.result.products.isNotEmpty()){
                        _productIdEvent.value = ProductIdUiEvent.Success(result.result)
                    }
                    else{
                        notFounded.value = product.value
                        _productIdEvent.value = ProductIdUiEvent.NotFounded
                    }
                }
                is Resource.Failure -> {
                    _productIdEvent.value = ProductIdUiEvent.Failure
                }
                is Resource.Loading -> {
                }
            }
        }
        // EditText Callback
        return true
    }

    sealed class ProductIdUiEvent {
        data class Success(val productId: ProductId) : ProductIdUiEvent()
        object Failure : ProductIdUiEvent()
        object NotFounded : ProductIdUiEvent()
        object Loading : ProductIdUiEvent()
        object Unit : ProductIdUiEvent()
    }
}