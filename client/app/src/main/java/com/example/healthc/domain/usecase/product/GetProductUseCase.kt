package com.example.healthc.domain.usecase.product

import com.example.healthc.domain.model.product.Product
import com.example.healthc.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend operator fun invoke(id: Int): Result<Product>{
        return productRepository.getProduct(id)
    }
}