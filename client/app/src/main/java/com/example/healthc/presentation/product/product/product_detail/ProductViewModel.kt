package com.example.healthc.presentation.product.product.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.usecase.product.GetNutritionLabelUseCase
import com.example.healthc.domain.usecase.product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getNutritionLabelUseCase: GetNutritionLabelUseCase
): ViewModel() {

    private val _productUiState : MutableStateFlow<Product> = MutableStateFlow(Product())
    val productUiState : StateFlow<Product> get() = _productUiState

    private val _productFact : MutableStateFlow<NutritionLabel> = MutableStateFlow(NutritionLabel())
    val productFact : StateFlow<NutritionLabel> get() = _productFact

    private val _productEvent = MutableSharedFlow<ProductEvent>()
    val productEvent: SharedFlow<ProductEvent> get() = _productEvent

    fun getProductInfo(id : Int){
        viewModelScope.launch {
            getProductUseCase(id)
                .onSuccess {  product ->
                    _productUiState.value = product
                }
                .onFailure { error ->
                    _productEvent.emit(ProductEvent.Failure(error.message))
                }
        }
    }

    fun getProductFact(id : Int){
        viewModelScope.launch {
            getNutritionLabelUseCase(id)
                .onSuccess {  label ->
                    _productFact.value = label
                }
                .onFailure { error ->
                    _productEvent.emit(ProductEvent.ImageFailure(error.message))
                }
        }
    }

    sealed class ProductEvent{
        data class Failure(val message : String?) : ProductEvent()
        data class ImageFailure(val message : String?) : ProductEvent()
    }
}