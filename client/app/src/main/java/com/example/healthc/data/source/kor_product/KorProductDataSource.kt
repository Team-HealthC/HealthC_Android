package com.example.healthc.data.source.kor_product

import com.example.healthc.data.model.remote.kor_product.KorProductResponse

interface KorProductDataSource {
    suspend fun getKorProductList(query: String): Result<List<KorProductResponse>>

    suspend fun getKorProduct(id: String): Result<KorProductResponse>
}