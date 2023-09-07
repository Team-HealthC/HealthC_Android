package com.healthc.app.presentation.product.kor_product.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthc.domain.model.kor_product.KorProduct
import com.healthc.domain.usecase.kor_product.GetKorProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KorProductViewModel @Inject constructor(
    private val getKorProductUseCase: GetKorProductUseCase
): ViewModel(){

    private val _korProductEvent: MutableSharedFlow<KorProductEvent> = MutableSharedFlow()
    val korProductEvent: SharedFlow<KorProductEvent> get() = _korProductEvent

    private val _korProductUiState: MutableStateFlow<KorProduct> = MutableStateFlow(KorProduct())
    val korProductUiState: StateFlow<KorProduct> get() = _korProductUiState


    fun getKorProduct(id: String){
        viewModelScope.launch {
            getKorProductUseCase(id)
                .onSuccess { korProduct ->
                    _korProductUiState.value = korProduct
                }
                .onFailure { e ->
                    _korProductEvent.emit(KorProductEvent.Failure(e.message))
                }
        }
    }

    sealed class KorProductEvent{
        data class Failure(val message: String?): KorProductEvent()

        object NotFounded: KorProductEvent()
    }
}