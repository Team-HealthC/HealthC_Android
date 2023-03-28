package com.example.healthc.data.repository

import com.example.healthc.data.source.food.kor_product.SearchFoodProductSource
import com.example.healthc.data.source.food.ingredient.SearchIngredientDataSource
import com.example.healthc.data.source.food.object_detect.SearchCategoryDataSource
import com.example.healthc.data.source.food.product.SearchProductFactsDataSource
import com.example.healthc.data.source.food.product.SearchProductIdDataSource
import com.example.healthc.data.source.food.product.SearchProductInfoDataSource
import com.example.healthc.data.source.food.recipe.SearchRecipeByIngDataSource
import com.example.healthc.domain.model.food.*
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val searchFoodDataSource: SearchIngredientDataSource,
    private val searchFoodProductSource : SearchFoodProductSource,
    private val searchCategoryDataSource: SearchCategoryDataSource,
    private val searchProductDataSource: SearchProductIdDataSource,
    private val searchProductInfoDataSource : SearchProductInfoDataSource,
    private val searchProductFactsDataSource: SearchProductFactsDataSource,
    private val searchRecipeByIngDataSource: SearchRecipeByIngDataSource
    ) : FoodRepository{
    override suspend fun searchIngredients(element: String): Resource<SearchFoodIngredient> {
        return searchFoodDataSource.searchIngredients(element)
    }

    override suspend fun searchFoodProduct(category: String): Resource<SearchFoodProduct> {
        return searchFoodProductSource.searchProduct(category)
    }

    override suspend fun searchFoodCategory(encodedImage: String): Resource<SearchFoodCategory> {
        return searchCategoryDataSource.searchFoodCategory(encodedImage)
    }

    override suspend fun searchFoodProductId(query: String): Resource<SearchProductId> {
        return searchProductDataSource.searchProduct(query)
    }

    override suspend fun searchFoodProductInfo(id: Int): Resource<SearchProductInfo> {
        return searchProductInfoDataSource.searchProductInfo(id)
    }

    override suspend fun searchFoodFacts(id: Int): Resource<String> {
        return searchProductFactsDataSource.searchProductFacts(id)
    }

    override suspend fun searchDish(ingredient: String): Resource<List<DishItem>> {
        return searchRecipeByIngDataSource.searchRecipeByIng(ingredient)
    }
}