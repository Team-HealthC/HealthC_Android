package com.example.healthc.data.service

import com.example.healthc.BuildConfig
import com.example.healthc.data.model.remote.product.ProductItemResultResponse
import com.example.healthc.data.model.remote.product.ProductResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface ProductService {
    // 가공식품 정보 찾기
    @GET("/food/products/{id}")
    suspend fun getProduct(
        @Path("id") id : Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY
    ) : ProductResponse

    // 가공식품 사진 및 id 찾기
    @GET("/food/products/search")
    suspend fun getProductId(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : ProductItemResultResponse

    // 가공식품 라벨 찾기
    @GET("/food/products/{id}/nutritionLabel.png")
    suspend fun getNutritionLabel(
        @Path("id") id : Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY,
        @Query("showIngredients") showIngredient : Boolean = true
    ) : ResponseBody
}