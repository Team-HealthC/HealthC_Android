package com.example.healthc.presentation.product.product.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.repository.ProductRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository : ProductRepository
): ViewModel() {

    private val _productUiState : MutableStateFlow<Product> = MutableStateFlow(Product())
    val productUiState : StateFlow<Product> get() = _productUiState

    private val _productFact : MutableStateFlow<NutritionLabel> = MutableStateFlow(NutritionLabel())
    val productFact : StateFlow<NutritionLabel> get() = _productFact

    private val _productInfoUiEvent = MutableStateFlow<UiEvent>(UiEvent.Unit)
    val productInfoUiEvent get() = _productInfoUiEvent

    fun getProductInfo(id : Int){
        viewModelScope.launch {
            val result = repository.getProduct(id)
            when(result){
                is Resource.Success -> {
                    _productUiState.value = result.result
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
            val result = repository.getNutritionLabel(id)
            when(result){
                is Resource.Success -> {
                    _productFact.value = result.result
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