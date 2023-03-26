package com.example.healthc.data.source.food.recipe

import com.example.healthc.domain.model.food.DishItem
import com.example.healthc.domain.utils.Resource

interface SearchRecipeByIngDataSource {
    suspend fun searchRecipeByIng(ingredient: String): Resource<List<DishItem>>
}