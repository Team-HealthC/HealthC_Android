package com.example.healthc.presentation.food.product.kor_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KorProductViewModel @Inject constructor(
    private val repository : FoodRepository
) : ViewModel() {
    private val _searchProductUiEvent = MutableStateFlow<SearchProductUiEvent>(
        SearchProductUiEvent.Unit)
    val searchProductUiEvent : StateFlow<SearchProductUiEvent>
        get() = _searchProductUiEvent

    val productName : MutableStateFlow<String> =MutableStateFlow<String>("")
    val notFounded : MutableStateFlow<String> =MutableStateFlow<String>("")

    fun getFoodProduct(){
        viewModelScope.launch {
            _searchProductUiEvent.value = SearchProductUiEvent.Loading
            val searchResult = repository.searchFoodProduct(
                requireNotNull(productName.value)
            )
            when(searchResult){
                is Resource.Success -> {
                    val result = searchResult.result
                    if(result.body.items.isNotEmpty()){
                        _searchProductUiEvent.value =
                            SearchProductUiEvent.Success(searchResult.result)
                    }else{
                        _searchProductUiEvent.value = SearchProductUiEvent.NotFounded
                        notFounded.value = productName.value
                    }
                }

                is Resource.Failure -> {
                    _searchProductUiEvent.value = SearchProductUiEvent.Failure
                }

                is Resource.Loading -> { }
            }
        }
    }

    sealed class SearchProductUiEvent {
        data class Success(val foodIngredient: SearchFoodProduct) : SearchProductUiEvent()
        object Failure : SearchProductUiEvent()
        object NotFounded: SearchProductUiEvent()
        object Loading : SearchProductUiEvent()
        object Unit : SearchProductUiEvent()
    }
}