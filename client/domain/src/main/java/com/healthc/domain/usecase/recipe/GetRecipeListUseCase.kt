package com.healthc.domain.usecase.recipe

import com.healthc.domain.model.recipe.Recipe
import com.healthc.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(ingredient: String): Result<List<Recipe>>{
        return recipeRepository.getRecipeList(ingredient)
    }
}