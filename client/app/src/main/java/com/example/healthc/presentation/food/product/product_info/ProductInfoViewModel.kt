package com.example.healthc.presentation.food.product.product_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchProductInfo
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductInfoViewModel @Inject constructor(
    private val repository : FoodRepository
): ViewModel() {
    val product : MutableStateFlow<SearchProductInfo> = MutableStateFlow(SearchProductInfo())
    val nutrients : MutableStateFlow<String> = MutableStateFlow<String>("")
    val productFact : MutableStateFlow<String> = MutableStateFlow<String>("")

    private val _productInfoUiEvent =
        MutableStateFlow<UiEvent>(UiEvent.Unit)
    val productInfoUiEvent get() = _productInfoUiEvent

    fun getProductInfo(id : Int){
        viewModelScope.launch {
            val result = repository.searchFoodProductInfo(id)
            when(result){
                is Resource.Success -> {
                    product.value = result.result
                    nutrients.value = result.result.nutrition.nutrients.joinToString(", ")
                }
                is Resource.Failure -> {
                    _productInfoUiEvent.value = UiEvent.Failure("상품 정보를 불러오는데 실패하였습니다.")
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    fun getProductFact(id : Int){
        viewModelScope.launch {
            val result = repository.searchFoodFacts(id)
            when(result){
                is Resource.Success -> {
                    productFact.value = result.result
                }
                is Resource.Failure -> {
                    _productInfoUiEvent.value = UiEvent.ImageFailure("영양성분표를 들고오는데 실패하였습니다.")
                }
                is Resource.Loading -> {}
            }
        }
    }
    sealed class UiEvent{
        data class Failure(val message : String?) : UiEvent()
        data class ImageFailure(val message : String?) : UiEvent()
        object Unit : UiEvent()
    }
}