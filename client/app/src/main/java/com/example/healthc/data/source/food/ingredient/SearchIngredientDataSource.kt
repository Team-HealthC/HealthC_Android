package com.example.healthc.data.source.food.ingredient

import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.utils.Resource

interface SearchIngredientDataSource {
    suspend fun searchIngredients(element : String) : Resource<SearchFoodIngredient>
}