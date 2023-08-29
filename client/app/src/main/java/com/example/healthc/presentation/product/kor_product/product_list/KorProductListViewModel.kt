package com.example.healthc.presentation.product.kor_product.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.usecase.kor_product.GetKorProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KorProductListViewModel @Inject constructor(
    private val getKorProductListUseCase: GetKorProductListUseCase
) : ViewModel() {
    private val _korProductListState = MutableStateFlow<KorProductUiState>(KorProductUiState.Init)
    val korProductListState : StateFlow<KorProductUiState> get() = _korProductListState

    private val _query: MutableStateFlow<String> = MutableStateFlow<String>("")
    val query: StateFlow<String> get() = _query

    fun getKorProductList(query: String){
        viewModelScope.launch {
            _query.value = query
            getKorProductListUseCase(query)
                .onSuccess { list ->
                    if(list.isEmpty()){
                        _korProductListState.value = KorProductUiState.NotFounded
                    }
                    else{
                        _korProductListState.value = KorProductUiState.Success(list)
                    }
                }
                .onFailure {
                    _korProductListState.value = KorProductUiState.Failure
                }
        }
    }

    sealed class KorProductUiState {
        object Init : KorProductUiState()
        data class Success(val foodIngredient: List<KorProduct>) : KorProductUiState()
        object Failure : KorProductUiState()
        object NotFounded: KorProductUiState()
    }
}