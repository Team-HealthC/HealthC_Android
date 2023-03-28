package com.example.healthc.data.source.food.ingredient

import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.data.source.food.ingredient.SearchIngredientDataSource
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchIngredientDataSourceImpl @Inject constructor(
    private val service : SearchFoodService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SearchIngredientDataSource {
    override suspend fun searchIngredients(element: String): Resource<SearchFoodIngredient>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.searchFoodIngredient(query = element).toSearchFoodIngredient()
            )
        } catch (e :Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}