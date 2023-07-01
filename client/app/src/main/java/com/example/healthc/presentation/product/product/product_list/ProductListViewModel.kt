package com.example.healthc.presentation.product.product.product_list

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
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _productListState = MutableStateFlow<ProductListUiState>(ProductListUiState.Init)
    val productListState : StateFlow<ProductListUiState> get() = _productListState

    private val _queryState : MutableStateFlow<String> = MutableStateFlow<String>("")
    val queryState : StateFlow<String> get() = _queryState

    fun getProductIds(query: String){
        viewModelScope.launch{
            _queryState.value = query
            val result = repository.getProductIds(_queryState.value)
            when(result){
                is Resource.Success -> {
                    if(result.result.products.isNotEmpty()){
                        _productListState.value = ProductListUiState.Success(result.result)
                    }
                    else{
                        _productListState.value = (ProductListUiState.NotFounded)
                    }
                }
                is Resource.Failure -> {
                    _productListState.value = (ProductListUiState.Failure)
                }
                is Resource.Loading -> {
                    
                }
            }
        }
    }

    sealed class ProductListUiState {
        object Init : ProductListUiState()
        data class Success(val productId: ProductId) : ProductListUiState()
        object Failure : ProductListUiState()
        object NotFounded : ProductListUiState()
    }
}