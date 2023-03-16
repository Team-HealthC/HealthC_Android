package com.example.healthc.data.source.food.product

import android.util.Base64
import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchProductFactsDataSourceImpl @Inject constructor(
    private val service : SearchFoodService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SearchProductFactsDataSource{
    override suspend fun searchProductFacts(id: Int): Resource<String>
    = withContext(coroutineDispatcher){
        try{
            val bytes = service.searchFoodFacts(id = id).bytes()
            Resource.Success(
                Base64.encodeToString(bytes, Base64.DEFAULT)
            )
        } catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}