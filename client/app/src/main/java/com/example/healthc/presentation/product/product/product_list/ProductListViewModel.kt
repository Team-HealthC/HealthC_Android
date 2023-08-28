package com.example.healthc.presentation.product.product.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.product.ProductItem
import com.example.healthc.domain.usecase.product.GetProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase
): ViewModel() {

    private val _productListState = MutableStateFlow<ProductListUiState>(ProductListUiState.Init)
    val productListState : StateFlow<ProductListUiState> get() = _productListState

    private val _query : MutableStateFlow<String> = MutableStateFlow<String>("")
    val query : StateFlow<String> get() = _query

    fun getProductIds(query: String){
        viewModelScope.launch{
            _query.value = query
            getProductListUseCase(query)
                .onSuccess { list ->
                    if(list.isEmpty()){
                        _productListState.value = (ProductListUiState.NotFounded)
                    }
                    else{
                        _productListState.value = ProductListUiState.Success(list)
                    }
                }
                .onFailure {
                    _productListState.value = ProductListUiState.Failure
                }
        }
    }

    sealed class ProductListUiState {
        object Init : ProductListUiState()
        data class Success(val productList: List<ProductItem>) : ProductListUiState()
        object Failure : ProductListUiState()
        object NotFounded : ProductListUiState()
    }
}