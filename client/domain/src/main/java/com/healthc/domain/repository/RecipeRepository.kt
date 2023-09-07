package com.healthc.domain.repository

import com.healthc.domain.model.recipe.Ingredient
import com.healthc.domain.model.recipe.Recipe

interface RecipeRepository {
    suspend fun getRecipeList(ingredient: String) : Result<List<Recipe>>

    suspend fun getIngredientList(detectedObject: String): Result<List<Ingredient>>
}