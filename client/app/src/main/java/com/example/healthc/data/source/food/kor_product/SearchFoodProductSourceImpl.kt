package com.example.healthc.data.source.food.kor_product

import com.example.healthc.data.remote.api.SearchFoodProductService
import com.example.healthc.data.source.food.kor_product.SearchFoodProductSource
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchFoodProductSourceImpl @Inject constructor(
    private val service : SearchFoodProductService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): SearchFoodProductSource {
    override suspend fun searchProduct(category: String): Resource<SearchFoodProduct>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.searchFoodProduct(prdlstNm = category).toSearchFoodProduct()
            )
        }
        catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}