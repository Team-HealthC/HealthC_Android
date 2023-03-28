package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.food.ingredient.SearchFoodIngredientDto
import com.example.healthc.data.dto.food.object_detect.SearchFoodCategoryDto
import com.example.healthc.data.dto.food.product.SearchProductIdDto
import com.example.healthc.data.dto.food.product.SearchProductInfoDto
import com.example.healthc.data.dto.food.recipe.RecipeByIngredientDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface SearchFoodService {

    // 해당 메뉴에 재료 찾기
    @GET("/recipes/complexSearch")
    suspend fun searchFoodIngredient(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 1,
        @Query("fillIngredients") fillIngredients : Boolean = true,
        @Query("ignorePantry") ignorePantry : Boolean = false
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
        @Path("id") id : Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Query("showIngredients") showIngredient : Boolean = true
    ) : ResponseBody

    // 가공식품 정보 찾기
    @GET("/food/products/{id}")
    suspend fun searchProductInformation(
        @Path("id") id : Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY
    ) : SearchProductInfoDto

    // 재료로 음식 레시피 검색하기
    @GET("/recipes/findByIngredients")
    suspend fun searchRecipeByIng(
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Query("ingredients") ingredients : String,
        @Query("number") number : Int = 10
    ) : List<RecipeByIngredientDto>
}