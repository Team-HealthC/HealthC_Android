package com.example.healthc.data.source.product

import com.example.healthc.data.remote.api.ProductService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val service : ProductService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ProductDataSource {
    override suspend fun getProduct(id: Int): Resource<Product>
    = withContext(coroutineDispatcher){
        try{
            Resource.Success(
                service.getProduct(id = id).toProduct()
            )
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getProductIds(query: String): Resource<ProductId>
            = withContext(coroutineDispatcher){
        try{
            Resource.Success(service.getProductId(query = query).toProductId())
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}