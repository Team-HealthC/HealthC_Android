package com.example.healthc.data.repository

import com.example.healthc.data.source.kor_product.KorProductDataSource
import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.repository.KorProductRepository
import timber.log.Timber
import javax.inject.Inject

class KorProductRepositoryImpl @Inject constructor(
    private val korProductDataSource: KorProductDataSource
) : KorProductRepository{
    override suspend fun getKorProductList(query: String): Result<List<KorProduct>> {
        return korProductDataSource.getKorProductList(query).mapCatching { list ->
            list.map{ it.toDomain() }
        }.onFailure { e ->
            Timber.e(e)
        }
    }

    override suspend fun getKorProduct(id: String): Result<KorProduct> {
        return korProductDataSource.getKorProduct(id).mapCatching { response ->
            response.toDomain()
        }
    }
}