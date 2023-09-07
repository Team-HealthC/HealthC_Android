package com.healthc.data.source.kor_product

import com.healthc.data.model.remote.kor_product.KorProductResponse
import com.healthc.data.service.KorProductService
import timber.log.Timber
import javax.inject.Inject

class KorProductDataSourceImpl @Inject constructor(
    private val service : KorProductService
): KorProductDataSource {
    override suspend fun getKorProductList(query: String): Result<List<KorProductResponse>>{
        return runCatching {
            service.getKorProductList(query = query).body.items.map{ it.item }
        }.onFailure { e ->
            e.printStackTrace()
            Timber.e(e)
        }
    }

    override suspend fun getKorProduct(id: String): Result<KorProductResponse> {
        return runCatching {
            service.getKorProduct(id = id).body.items.map{ it.item }.first()
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}