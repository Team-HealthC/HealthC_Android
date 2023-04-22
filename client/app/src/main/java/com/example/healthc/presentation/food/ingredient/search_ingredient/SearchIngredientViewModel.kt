package com.example.healthc.presentation.food.ingredient.search_ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.food.DishItem
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import com.example.healthc.presentation.utils.toIngredientEng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchIngredientViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _searchDishUiEvent = MutableStateFlow<SearchDishUiEvent>(
        SearchDishUiEvent.Unit)
    val searchDishUiEvent : StateFlow<SearchDishUiEvent>
            get() = _searchDishUiEvent

    private val _allergy = MutableLiveData<String>("")
    val allergy : LiveData<String> get() = _allergy

    fun setAllergy(allergy: String){
        _allergy.value = allergy
    }

    fun getFoodIngredient(){
        viewModelScope.launch {
            val searchResult = foodRepository.searchDish(
                requireNotNull(_allergy.value).toIngredientEng()
            )
            when(searchResult){
                is Resource.Success -> {
                    if(searchResult.result.isNotEmpty()){
                        _searchDishUiEvent.value =
                            SearchDishUiEvent.Success(searchResult.result)
                    }else{
                        _searchDishUiEvent.value = SearchDishUiEvent.NotFounded
                    }
                }

                is Resource.Failure -> {
                    _searchDishUiEvent.value = SearchDishUiEvent.Failure
                }

                is Resource.Loading -> { }
            }
        }
    }

    sealed class SearchDishUiEvent {
        data class Success(val dish: List<DishItem>) : SearchDishUiEvent()
        object Failure : SearchDishUiEvent()
        object NotFounded : SearchDishUiEvent()
        object Unit : SearchDishUiEvent()
    }
}