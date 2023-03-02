package com.example.healthc.presentation.food.ingredient.search_ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchIngredientViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _searchDictionaryUiEvent = MutableStateFlow<SearchDictionaryUiEvent>(
        SearchDictionaryUiEvent.Unit)
    val searchDictionaryUiEvent : StateFlow<SearchDictionaryUiEvent>
            get() = _searchDictionaryUiEvent

    private val _allergy = MutableLiveData<String>("")
    val allergy : LiveData<String> get() = _allergy

    fun setAllergy(allergy: String){
        _allergy.value = allergy
    }

    fun getFoodIngredient(){
        viewModelScope.launch {
            val searchResult = foodRepository.searchFoodMenu(
                requireNotNull(_allergy.value)
            )
            when(searchResult){
                is Resource.Success -> {
                    _searchDictionaryUiEvent.value =
                        SearchDictionaryUiEvent.Success(searchResult.result)
                }

                is Resource.Failure -> {
                    _searchDictionaryUiEvent.value = SearchDictionaryUiEvent.Failure
                }

                is Resource.Loading -> { }
            }
        }
    }

    sealed class SearchDictionaryUiEvent {
        data class Success(val foodIngredient: SearchFoodIngredient) : SearchDictionaryUiEvent()
        object Failure : SearchDictionaryUiEvent()
        object Unit : SearchDictionaryUiEvent()
    }
}