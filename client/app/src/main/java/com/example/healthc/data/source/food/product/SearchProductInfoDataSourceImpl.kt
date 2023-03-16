package com.example.healthc.data.source.food.product

import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.domain.model.food.SearchProductInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchProductInfoDataSourceImpl @Inject constructor(
    private val service : SearchFoodService,
    private val coroutineDispatcher: CoroutineDispatcher
) : SearchProductInfoDataSource{
    override suspend fun searchProductInfo(id: Int): Resource<SearchProductInfo>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.searchProductInformation(id = id)
                    .toSearchProductInfo()
            )
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}