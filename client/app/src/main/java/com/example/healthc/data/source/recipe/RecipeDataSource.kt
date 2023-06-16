package com.example.healthc.data.source.recipe

import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.utils.Resource

interface RecipeDataSource {
    suspend fun getRecipes(ingredient: String): Resource<List<Recipe>>

    suspend fun getIngredients(dish : String) : Resource<Ingredient>

    suspend fun getNutritionLabel(id: Int): Resource<NutritionLabel>

}