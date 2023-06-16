package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.product.ProductDto
import com.example.healthc.data.dto.product.ProductIdDto
import retrofit2.http.*

interface ProductService {
    // 가공식품 사진 및 id 찾기
    @GET("/food/products/search")
    suspend fun getProductId(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Query("query") query : String,
        @Query("number") number : Int = 10
    ) : ProductIdDto

    // 가공식품 정보 찾기
    @GET("/food/products/{id}")
    suspend fun getProduct(
        @Path("id") id : Int,
        @Query("apiKey") apiKey: String = BuildConfig.SPOON_API_KEY
    ) : ProductDto
}