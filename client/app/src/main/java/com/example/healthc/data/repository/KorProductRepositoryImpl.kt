package com.example.healthc.data.repository

import com.example.healthc.data.source.kor_product.KorProductDataSource
import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.repository.KorProductRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class KorProductRepositoryImpl @Inject constructor(
    private val korProductDataSource: KorProductDataSource
) : KorProductRepository{
    override suspend fun getKorProducts(query: String): Resource<KorProduct> {
        return korProductDataSource.getKorProduct(query)
    }
}