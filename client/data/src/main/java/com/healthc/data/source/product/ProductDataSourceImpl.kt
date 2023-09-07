package com.healthc.data.source.product

import com.healthc.data.model.remote.product.ProductItemResponse
import com.healthc.data.model.remote.product.ProductResponse
import com.healthc.data.model.remote.recipe.NutritionLabelResponse
import com.healthc.data.service.ProductService
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val service : ProductService
) : ProductDataSource {
    override suspend fun getProduct(id: Int): Result<ProductResponse>{
        return runCatching {
            service.getProduct(id = id)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun getProductList(query: String): Result<List<ProductItemResponse>>{
        return runCatching {
            service.getProductId(query = query).products
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun getNutritionLabel(id: Int): Result<NutritionLabelResponse>{
        return runCatching {
            NutritionLabelResponse(
                service.getNutritionLabel(id = id).bytes()
            )
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}