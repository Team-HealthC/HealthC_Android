package com.example.healthc.data.source.food

import com.example.healthc.data.remote.api.SearchFoodProductService
import com.example.healthc.domain.model.food.SearchFoodProduct
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class SearchFoodProductSourceImpl @Inject constructor(
    private val service : SearchFoodProductService
): SearchFoodProductSource{
    override suspend fun searchProduct(category: String): Resource<SearchFoodProduct> {
        return try{
            Resource.Success(
                service.searchFoodProduct(prdlstNm = category).toSearchFoodProduct()
            )
        }
        catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}