package com.example.healthc.domain.repository

import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.recipe.Recipe

interface RecipeRepository {
    suspend fun getRecipeList(ingredient: String) : Result<List<Recipe>>

    suspend fun getIngredientList(detectedObject: String): Result<List<Ingredient>>
}