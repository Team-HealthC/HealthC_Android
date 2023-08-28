package com.example.healthc.data.repository

import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataSource: RecipeDataSource,
) : RecipeRepository{
    override suspend fun getRecipeList(ingredient: String): Result<List<Recipe>> {
        return recipeDataSource.getRecipeList(ingredient).mapCatching { list ->
            list.map{ it.toRecipe() }
        }
    }

    override suspend fun getIngredientList(detectedObject: String): Result<List<Ingredient>> {
        return recipeDataSource.getIngredientList(detectedObject).mapCatching { list ->
            list.map{ it.toDomain() }
        }
    }
}