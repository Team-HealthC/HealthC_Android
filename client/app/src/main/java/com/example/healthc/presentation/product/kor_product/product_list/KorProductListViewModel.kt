package com.example.healthc.presentation.product.kor_product.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.kor_product.KorProductInfo
import com.example.healthc.domain.repository.KorProductRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KorProductListViewModel @Inject constructor(
    private val repository : KorProductRepository
) : ViewModel() {
    private val _korProductListUiState = MutableStateFlow<KorProductUiState>(KorProductUiState.Init)
    val korProductListUiState : StateFlow<KorProductUiState> get() = _korProductListUiState

    private val _queryUiState : MutableStateFlow<String> =MutableStateFlow<String>("")
    val queryUiState : StateFlow<String> get() = _queryUiState

    fun getKorProductIds(query: String){
        viewModelScope.launch {
            _queryUiState.value = query
            val searchResult = repository.getKorProducts(_queryUiState.value)
            when(searchResult){
                is Resource.Loading -> { }

                is Resource.Success -> {
                    val result = searchResult.result
                    if(result.items.isNotEmpty()){
                        _korProductListUiState.value = KorProductUiState.Success(result.items)
                    }else{
                        _korProductListUiState.value = KorProductUiState.NotFounded
                    }
                }

                is Resource.Failure -> {
                    _korProductListUiState.value = KorProductUiState.Failure
                }
            }
        }
    }

    sealed class KorProductUiState {
        object Init : KorProductUiState()
        data class Success(val foodIngredient: List<KorProductInfo>) : KorProductUiState()
        object Failure : KorProductUiState()
        object NotFounded: KorProductUiState()
    }
}