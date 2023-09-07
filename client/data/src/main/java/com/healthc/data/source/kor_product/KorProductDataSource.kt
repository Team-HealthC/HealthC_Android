package com.healthc.data.source.kor_product

import com.healthc.data.model.remote.kor_product.KorProductResponse

interface KorProductDataSource {
    suspend fun getKorProductList(query: String): Result<List<KorProductResponse>>

    suspend fun getKorProduct(id: String): Result<KorProductResponse>
}