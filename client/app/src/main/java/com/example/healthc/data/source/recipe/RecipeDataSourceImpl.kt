package com.example.healthc.data.source.recipe

import android.util.Base64
import com.example.healthc.data.remote.api.RecipeService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeDataSourceImpl @Inject constructor(
    private val service : RecipeService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : RecipeDataSource {

    override suspend fun getRecipes(ingredient: String): Resource<List<Recipe>>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.getRecipes(ingredients = ingredient).map{
                    it.toRecipe()
                }
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getIngredients(dish: String): Resource<Ingredient>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.getIngredients(query = dish).toIngredient()
            )
        } catch (e :Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getNutritionLabel(id: Int): Resource<NutritionLabel>
    = withContext(coroutineDispatcher){
        try{
            val bytes = service.getNutritionLabel(id = id).bytes()
            Resource.Success(
                NutritionLabel(Base64.encodeToString(bytes, Base64.DEFAULT))
            )
        } catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}