package com.healthc.domain.repository

import com.healthc.domain.model.kor_product.KorProduct

interface KorProductRepository {
    suspend fun getKorProductList(query: String): Result<List<KorProduct>>

    suspend fun getKorProduct(id: String): Result<KorProduct>
}