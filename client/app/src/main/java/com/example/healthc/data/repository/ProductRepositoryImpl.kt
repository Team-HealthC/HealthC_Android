package com.example.healthc.data.repository

import com.example.healthc.data.source.product.ProductDataSource
import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.repository.ProductRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource : ProductDataSource,
    private val recipeDataSource: RecipeDataSource
) : ProductRepository{
    override suspend fun getProduct(id: Int): Resource<Product> {
        return productDataSource.getProduct(id)
    }

    override suspend fun getProductIds(query: String): Resource<ProductId> {
        return productDataSource.getProductIds(query)
    }

    override suspend fun getNutritionLabel(id: Int): Resource<NutritionLabel> {
        return recipeDataSource.getNutritionLabel(id)
    }
}