package com.healthc.data.source.recipe

import com.healthc.data.model.remote.recipe.IngredientResponse
import com.healthc.data.model.remote.recipe.RecipeResponse

interface RecipeDataSource {
    suspend fun getRecipeList(ingredient: String): Result<List<RecipeResponse>>

    suspend fun getIngredientList(detectedObject : String) : Result<List<IngredientResponse>>
}