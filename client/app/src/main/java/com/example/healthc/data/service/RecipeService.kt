package com.example.healthc.data.service

import com.example.healthc.BuildConfig
import com.example.healthc.data.model.remote.recipe.IngredientResultResponse
import com.example.healthc.data.model.remote.recipe.RecipeResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("/recipes/findByIngredients")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Query("ingredients") ingredients : String,
        @Query("number") number : Int = 10
    ) : List<RecipeResponse>

    // 해당 메뉴에 재료 찾기
    @GET("/recipes/complexSearch")
    suspend fun getIngredients(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 1,
        @Query("fillIngredients") fillIngredients : Boolean = true,
        @Query("ignorePantry") ignorePantry : Boolean = false
    ) : IngredientResultResponse
}