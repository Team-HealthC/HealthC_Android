package com.example.healthc.data.source.kor_product

import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.utils.Resource

interface KorProductDataSource {
    suspend fun getKorProduct(category: String): Resource<KorProduct>
}