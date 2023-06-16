package com.example.healthc.data.source.kor_product

import com.example.healthc.data.remote.api.KorProductService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KorProductDataSourceImpl @Inject constructor(
    private val service : KorProductService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): KorProductDataSource {
    override suspend fun getKorProduct(category: String): Resource<KorProduct>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.getKorProduct(prdlstNm = category).body.toKorProduct()
            )
        }
        catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}