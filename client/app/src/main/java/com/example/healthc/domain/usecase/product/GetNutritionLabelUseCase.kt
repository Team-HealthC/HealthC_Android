package com.example.healthc.domain.usecase.product

import com.example.healthc.domain.model.product.NutritionLabel
import com.example.healthc.domain.repository.ProductRepository
import javax.inject.Inject

class GetNutritionLabelUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend operator fun invoke(id: Int): Result<NutritionLabel>{
        return productRepository.getNutritionLabel(id)
    }
}