package com.example.healthc.data.source.food

import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.utils.Resource

interface SearchIngredientDataSource {
    suspend fun searchFoodMenu(element : String) : Resource<SearchFoodIngredient>
}