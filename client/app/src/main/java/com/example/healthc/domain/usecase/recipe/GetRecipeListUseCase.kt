package com.example.healthc.domain.usecase.recipe

import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(ingredient: String): Result<List<Recipe>>{
        return recipeRepository.getRecipeList(ingredient)
    }
}