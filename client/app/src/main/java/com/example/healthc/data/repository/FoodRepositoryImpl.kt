package com.example.healthc.data.repository

import com.example.healthc.data.source.food.SearchIngredientDataSource
import com.example.healthc.domain.model.food.SearchFoodIngredient
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val SearchFoodDataSource: SearchIngredientDataSource
) : FoodRepository{
    override suspend fun searchFoodMenu(element: String): Resource<SearchFoodIngredient> {
        return SearchFoodDataSource.searchFoodMenu(element)
    }
}