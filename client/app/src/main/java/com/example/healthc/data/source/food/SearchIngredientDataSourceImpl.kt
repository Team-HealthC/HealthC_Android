package com.example.healthc.data.source.food

import com.example.healthc.data.remote.api.SearchFoodIngredientService
import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class SearchIngredientDataSourceImpl @Inject constructor(
    private val service : SearchFoodIngredientService
) : SearchIngredientDataSource{
    override suspend fun searchFoodMenu(element: String): Resource<SearchFoodIngredient> {
        return try{
            Resource.Success(
                service.searchFoodIngredient(query = element).toSearchFoodIngredient()
            )
        } catch (e :Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}