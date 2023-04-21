package com.example.healthc.presentation.food.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchProductId
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: FoodRepository
): ViewModel() {

    private val _productIdEvent = MutableStateFlow<ProductIdUiEvent>(
        ProductIdUiEvent.Unit
    )
    val productIdEvent : StateFlow<ProductIdUiEvent>
        get() = _productIdEvent

    val product : MutableStateFlow<String> = MutableStateFlow<String>("")
    val notFounded : MutableStateFlow<String> = MutableStateFlow<String>("")

    fun getProductIds(){
        viewModelScope.launch{
            val result = repository.searchFoodProductId(
                requireNotNull(product.value)
            )
            when(result){
                is Resource.Success -> {
                    if(result.result.products.isNotEmpty()){
                        _productIdEvent.value = ProductIdUiEvent.Success(result.result)
                    }else{
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
    }

    sealed class ProductIdUiEvent {
        data class Success(val productId: SearchProductId) : ProductIdUiEvent()
        object Failure : ProductIdUiEvent()
        object NotFounded : ProductIdUiEvent()
        object Unit : ProductIdUiEvent()
    }
}