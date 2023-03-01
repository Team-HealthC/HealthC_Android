package com.example.healthc.domain.repository

import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.utils.Resource

interface FoodRepository {
    suspend fun searchFoodMenu(element : String): Resource<SearchFoodIngredient>
}