package com.example.healthc.presentation.camera.object_detect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.use_case.DetectText
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCategoryViewModel @Inject constructor(
    private val repository : FoodRepository,
    private val detectTextUseCase :  DetectText
) : ViewModel() {

    // imageUrl
    private val _imageUrl = MutableStateFlow("")
    val imageUrl : StateFlow<String>
        get() = _imageUrl

    private val _searchCategoryUiEvent = MutableStateFlow<UiEvent>(UiEvent.Unit)
    val searchCategoryUiEvent get() = _searchCategoryUiEvent

    fun searchCategory(encodedImage: String) {
        viewModelScope.launch {
            val result = repository.searchFoodCategory(encodedImage)
            when(result){
                is Resource.Loading -> {}

                is Resource.Success -> {
                    _searchCategoryUiEvent.value = UiEvent.Success(result.result)
                }
                is Resource.Failure -> {
                    _searchCategoryUiEvent.value = UiEvent.Failure(
                        result.exception.message.toString()
                    )
                }
            }
        }
    }

    fun searchIngredients(dish: String){
        viewModelScope.launch {
            val result = repository.searchIngredients(dish)
            when(result){
                is Resource.Loading -> {}

                is Resource.Success -> {
                    val ingredients = result.result.ingredients.joinToString(", ")
                    detectElements(ingredients)
                }
                is Resource.Failure -> {
                    _searchCategoryUiEvent.value = UiEvent.Failure(
                        result.exception.message.toString()
                    )
                }
            }
        }
    }

    private fun detectElements(ingredients: String){
        viewModelScope.launch {
            val result = detectTextUseCase(ingredients, isEnglish = true)
            if(result.successful){
                if(result.detected) {
                    _searchCategoryUiEvent.value = UiEvent.Detected(result.detectedList)
                }else{
                    _searchCategoryUiEvent.value = UiEvent.DetectNoting
                }
            }
            else{
                _searchCategoryUiEvent.value = UiEvent.Failure("회원 정보를 가져오는데 실패하였습니다.")
            }
        }
    }

    fun setImageUrl(imageUrl: String){
        _imageUrl.value = imageUrl
    }

    sealed class UiEvent {
        data class Failure(val message : String) : UiEvent()
        data class Success(val category: SearchFoodCategory) : UiEvent()
        data class Detected(val detectedList : List<String>) : UiEvent()
        object DetectNoting : UiEvent()
        object Unit : UiEvent()
    }
}