package com.healthc.domain.usecase.recipe

import com.healthc.domain.model.recipe.Ingredient
import com.healthc.domain.repository.RecipeRepository
import javax.inject.Inject

class GetIngredientListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
){
    suspend operator fun invoke(dish: String): Result<List<Ingredient>>{
        return recipeRepository.getIngredientList(dish)
    }
}