package com.example.healthc.data.source.food.product

import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.domain.model.food.SearchProductId
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchProductIdDataSourceImpl @Inject constructor(
    private val service : SearchFoodService,
    private val coroutineDispatcher: CoroutineDispatcher
) : SearchProductIdDataSource {
    override suspend fun searchProduct(query: String): Resource<SearchProductId>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.searchProductId(query = query)
                    .toSearchProductId()
            )
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}