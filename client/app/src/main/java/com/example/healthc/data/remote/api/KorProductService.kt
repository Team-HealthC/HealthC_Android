package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.kor_product.KorProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KorProductService {

    @GET("/B553748/CertImgListService/getCertImgListService")
    suspend fun getKorProduct(
        @Query("ServiceKey") ServiceKey : String = BuildConfig.DATA_GO_PRODUCT_API_KEY,
        @Query("returnType") returnType : String = "json",
        @Query("prdlstNm") prdlstNm : String
    ) : KorProductResponse

}