package com.example.healthc.data.source.recipe

import com.example.healthc.data.model.remote.recipe.IngredientResponse
import com.example.healthc.data.model.remote.recipe.NutritionLabelResponse
import com.example.healthc.data.model.remote.recipe.RecipeResponse

interface RecipeDataSource {
    suspend fun getRecipeList(ingredient: String): Result<List<RecipeResponse>>

    suspend fun getIngredientList(detectedObject : String) : Result<List<IngredientResponse>>
}