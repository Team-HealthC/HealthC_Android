package com.healthc.data.source.recipe

import com.healthc.data.model.remote.recipe.IngredientResponse
import com.healthc.data.model.remote.recipe.RecipeResponse
import com.healthc.data.service.RecipeService
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(
    private val service : RecipeService
) : RecipeDataSource {

    override suspend fun getRecipeList(ingredient: String): Result<List<RecipeResponse>>{
        return runCatching {
            service.getRecipes(ingredients = ingredient)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun getIngredientList(detectedObject: String): Result<List<IngredientResponse>>{
        return runCatching {
            val list = mutableListOf<IngredientResponse>()
            service.getIngredients(query = detectedObject).results.forEach{ result ->
                result.missedIngredients.forEach{ ingredient ->
                    list.add(ingredient)
                }
            }
            list
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}