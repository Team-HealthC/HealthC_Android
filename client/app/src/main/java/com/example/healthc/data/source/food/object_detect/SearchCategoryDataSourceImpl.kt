package com.example.healthc.data.source.food.object_detect

import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.utils.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SearchCategoryDataSourceImpl @Inject constructor(
    private val service : SearchFoodService
) : SearchCategoryDataSource{
    override suspend fun searchFoodCategory(fileUrl : String, file: File): Resource<SearchFoodCategory> {
        return try{
            val result = service.searchFoodCategory(
                file = MultipartBody.Part.createFormData(
                    "file",
                    fileUrl,
                    file.asRequestBody()
                )
            ).toSearchFoodCategory()
            Resource.Success(result)
        } catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}