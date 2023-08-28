package com.example.healthc.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.usecase.recipe.GetRecipeListUseCase
import com.example.healthc.utils.toIngredientEng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
): ViewModel() {

    private val _recipeEvent = MutableSharedFlow<RecipeEvent>()
    val recipeEvent : SharedFlow<RecipeEvent> get() = _recipeEvent

    private val _recipeListUiState: MutableStateFlow<RecipeUiState>
        = MutableStateFlow(RecipeUiState.Init)
    val recipeListUiState: StateFlow<RecipeUiState> get() = _recipeListUiState

    private val _allergy: MutableStateFlow<String> = MutableStateFlow<String>("")
    val allergy: StateFlow<String> get() = _allergy

    fun setAllergy(allergy: String){
        _allergy.value = allergy
    }

    fun getFoodIngredient(){
        viewModelScope.launch {
            getRecipeListUseCase(_allergy.value.toIngredientEng())
                .onSuccess { list ->
                    if(list.isNotEmpty()){
                        _recipeListUiState.emit(RecipeUiState.Success(list))
                    }
                    else{
                        _recipeListUiState.emit(RecipeUiState.NotFounded)
                    }
                }
                .onFailure {
                    _recipeEvent.emit(RecipeEvent.Failure)
                }
        }
    }

    sealed class RecipeUiState{
        object Init: RecipeUiState()
        object NotFounded: RecipeUiState()
        data class Success(val list: List<Recipe>): RecipeUiState()
    }

    sealed class RecipeEvent {
        object Failure : RecipeEvent()
    }
}