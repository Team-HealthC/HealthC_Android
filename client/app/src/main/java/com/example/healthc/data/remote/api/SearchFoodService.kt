package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.food.ingredient.SearchFoodIngredientDto
import com.example.healthc.data.dto.food.object_detect.SearchFoodCategoryDto
import com.example.healthc.data.dto.food.product.SearchProductIdDto
import com.example.healthc.data.dto.food.product.SearchProductInfoDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface SearchFoodService {

    // 해당 재표가 포함된 메뉴 찾기
    @GET("/food/ingredients/search")
    suspend fun searchFoodIngredient(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : SearchFoodIngredientDto

    // 음식 사진 인식
    @Multipart
    @POST("/food/images/classify")
    suspend fun searchFoodCategory(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Part file: MultipartBody.Part,
    ) : SearchFoodCategoryDto

    // 가공식품 사진 및 id 찾기
    @GET("/food/products/search")
    suspend fun searchProductId(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : SearchProductIdDto

    // 가공식품 라벨 찾기
    @GET("/recipes/{id}/nutritionLabel.png")
    suspend fun searchFoodFacts(
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Path("id") id : Int,
        @Query("showIngredients") showIngredient : Boolean = true
    ) : ResponseBody

    // 가공식품 정보 찾기
    @GET("/food/products/{id}")
    suspend fun searchProductInformation(
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Path("id") id : Int,
    ) : SearchProductInfoDto
}