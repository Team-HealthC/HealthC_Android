package com.example.healthc.presentation.camera.object_detect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val repository : FoodRepository
) : ViewModel() {

    // imageUrl
    private val _imageUrl = MutableStateFlow("")
    val imageUrl : StateFlow<String>
        get() = _imageUrl

    private val _searchCategoryUiEvent = MutableStateFlow<SearchCategoryUiEvent>(SearchCategoryUiEvent.Unit)
    val searchCategoryUiEvent get() = _searchCategoryUiEvent

    fun searchCategory(encodedImage: String) {
        viewModelScope.launch {
            val result = repository.searchFoodCategory(encodedImage)
            when(result){
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _searchCategoryUiEvent.value = SearchCategoryUiEvent.Success(result.result)
                }
                is Resource.Failure -> {
                    _searchCategoryUiEvent.value = SearchCategoryUiEvent.Failure(
                        result.exception.message.toString()
                    )
                }
            }
        }
    }

    fun setImageUrl(imageUrl: String){
        _imageUrl.value = imageUrl
    }

    sealed class SearchCategoryUiEvent {
        data class Failure(val message : String) : SearchCategoryUiEvent()
        data class Success(val category: SearchFoodCategory) : SearchCategoryUiEvent()
        object Unit : SearchCategoryUiEvent()
    }
}