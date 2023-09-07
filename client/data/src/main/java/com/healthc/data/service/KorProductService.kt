package com.healthc.data.service

import com.healthc.data.BuildConfig
import com.healthc.data.model.remote.kor_product.KorProductResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KorProductService {

    @GET("/B553748/CertImgListService/getCertImgListService")
    suspend fun getKorProductList(
        @Query("ServiceKey") serviceKey: String = BuildConfig.DATA_GO_PRODUCT_API_KEY,
        @Query("returnType") returnType: String = "json",
        @Query("prdlstNm") query: String
    ) : KorProductResultResponse

    @GET("/B553748/CertImgListService/getCertImgListService")
    suspend fun getKorProduct(
        @Query("ServiceKey") serviceKey: String = BuildConfig.DATA_GO_PRODUCT_API_KEY,
        @Query("returnType") returnType: String = "json",
        @Query("prdlstReportNo") id: String
    ) : KorProductResultResponse

}