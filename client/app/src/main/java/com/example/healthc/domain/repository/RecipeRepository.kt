package com.example.healthc.domain.repository

import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.utils.Resource

interface RecipeRepository {
    suspend fun getRecipes(ingredient: String) : Resource<List<Recipe>>
}