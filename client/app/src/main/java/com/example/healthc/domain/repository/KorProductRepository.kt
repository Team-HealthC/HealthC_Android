package com.example.healthc.domain.repository

import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.utils.Resource

interface KorProductRepository {
    suspend fun getKorProducts(query: String): Resource<KorProduct>
}