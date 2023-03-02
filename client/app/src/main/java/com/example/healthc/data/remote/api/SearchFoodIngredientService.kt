package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.food.SearchFoodIngredientDto
import com.example.healthc.data.dto.food.SearchFoodProductDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchFoodIngredientService {

    @GET("/food/ingredients/search")
    suspend fun searchFoodIngredient(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : SearchFoodIngredientDto

}