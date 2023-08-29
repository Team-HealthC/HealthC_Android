package com.example.healthc.domain.usecase.kor_product

import com.example.healthc.domain.model.kor_product.KorProduct
import com.example.healthc.domain.repository.KorProductRepository
import javax.inject.Inject

class GetKorProductListUseCase @Inject constructor(
    private val korProductRepository: KorProductRepository
){
    suspend operator fun invoke(query: String): Result<List<KorProduct>>{
        return korProductRepository.getKorProductList(query)
    }
}