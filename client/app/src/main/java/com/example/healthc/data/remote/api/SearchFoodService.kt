package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.food.object_detect.SearchFoodCategoryDto
import com.example.healthc.data.dto.food.SearchFoodIngredientDto
import okhttp3.MultipartBody
import retrofit2.http.*

interface SearchFoodService {

    @GET("/food/ingredients/search")
    suspend fun searchFoodIngredient(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : SearchFoodIngredientDto

    @Multipart
    @POST("/food/images/classify")
    suspend fun searchFoodCategory(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Part file: MultipartBody.Part,
    ) : SearchFoodCategoryDto

}