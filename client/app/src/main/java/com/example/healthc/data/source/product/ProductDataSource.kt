package com.example.healthc.data.source.product

import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.model.product.ProductId
import com.example.healthc.domain.utils.Resource

interface ProductDataSource {
    suspend fun getProductIds(query: String) : Resource<ProductId>
    suspend fun getProduct(id: Int) : Resource<Product>

}