package com.example.healthc.data.source.kor_product

import com.example.healthc.data.model.remote.kor_product.KorProductResponse

interface KorProductDataSource {
    suspend fun getKorProductList(category: String): Result<List<KorProductResponse>>
}