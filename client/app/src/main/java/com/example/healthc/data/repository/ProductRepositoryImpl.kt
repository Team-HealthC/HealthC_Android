package com.example.healthc.data.repository

import com.example.healthc.data.source.product.ProductDataSource
import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductItem
import com.example.healthc.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource : ProductDataSource
) : ProductRepository{
    override suspend fun getProduct(id: Int): Result<Product> {
        return productDataSource.getProduct(id).mapCatching { response ->
            response.toDomain()
        }
    }

    override suspend fun getProductList(query: String): Result<List<ProductItem>> {
        return productDataSource.getProductList(query).mapCatching { list ->
            list.map{ it.toDomain() }
        }
    }

    override suspend fun getNutritionLabel(id: Int): Result<NutritionLabel> {
        return productDataSource.getNutritionLabel(id).mapCatching { label ->
            label.toDomain()
        }
    }
}