package com.example.healthc.domain.usecase.product

import com.example.healthc.domain.model.product.ProductItem
import com.example.healthc.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend operator fun invoke(query: String): Result<List<ProductItem>>{
        return productRepository.getProductList(query)
    }
}