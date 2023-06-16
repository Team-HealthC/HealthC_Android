package com.example.healthc.data.repository

import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.domain.model.recipe.Recipe
import com.example.healthc.domain.repository.RecipeRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val dataSource: RecipeDataSource
) : RecipeRepository{
    override suspend fun getRecipes(ingredient: String): Resource<List<Recipe>> {
        return dataSource.getRecipes(ingredient)
    }
}