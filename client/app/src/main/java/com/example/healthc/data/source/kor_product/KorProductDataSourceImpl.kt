package com.example.healthc.data.source.kor_product

import com.example.healthc.data.model.remote.kor_product.KorProductResponse
import com.example.healthc.data.service.KorProductService
import timber.log.Timber
import javax.inject.Inject

class KorProductDataSourceImpl @Inject constructor(
    private val service : KorProductService
): KorProductDataSource {
    override suspend fun getKorProductList(category: String): Result<List<KorProductResponse>>{
        return runCatching {
            service.getKorProduct(prdlstNm = category).body.items.map{ it.item }
        }.onFailure { e ->
            e.printStackTrace()
            Timber.e(e)
        }
    }
}