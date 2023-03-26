package com.example.healthc.domain.repository

import com.example.healthc.domain.model.food.*
import com.example.healthc.domain.utils.Resource

interface FoodRepository {
    suspend fun searchFoodMenu(element : String): Resource<SearchFoodIngredient>

    suspend fun searchFoodProduct(category: String): Resource<SearchFoodProduct>

    suspend fun searchFoodCategory(encodedImage: String) : Resource<SearchFoodCategory>

    suspend fun searchFoodProductId(query: String) : Resource<SearchProductId>

    suspend fun searchFoodProductInfo(id: Int) : Resource<SearchProductInfo>

    suspend fun searchFoodFacts(id : Int) : Resource<String>

    suspend fun searchDish(ingredient: String) : Resource<List<DishItem>>

}