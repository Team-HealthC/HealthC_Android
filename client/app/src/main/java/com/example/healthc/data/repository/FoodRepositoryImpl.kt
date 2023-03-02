package com.example.healthc.data.repository

import com.example.healthc.data.source.food.SearchFoodProductSource
import com.example.healthc.data.source.food.SearchIngredientDataSource
import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val searchFoodDataSource: SearchIngredientDataSource,
    private val searchFoodProductSource : SearchFoodProductSource
) : FoodRepository{
    override suspend fun searchFoodMenu(element: String): Resource<SearchFoodIngredient> {
        return searchFoodDataSource.searchFoodMenu(element)
    }

    override suspend fun searchFoodProduct(category: String): Resource<SearchFoodProduct> {
        return searchFoodProductSource.searchProduct(category)
    }
}