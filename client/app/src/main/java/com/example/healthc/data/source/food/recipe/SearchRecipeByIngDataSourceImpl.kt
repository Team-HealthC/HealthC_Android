package com.example.healthc.data.source.food.recipe

import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.food.DishItem
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRecipeByIngDataSourceImpl @Inject constructor(
    private val service : SearchFoodService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : SearchRecipeByIngDataSource{

    override suspend fun searchRecipeByIng(ingredient: String): Resource<List<DishItem>>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.searchRecipeByIng(ingredients = ingredient).map{
                    it.toDishItem()
                }
            )
        } catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}