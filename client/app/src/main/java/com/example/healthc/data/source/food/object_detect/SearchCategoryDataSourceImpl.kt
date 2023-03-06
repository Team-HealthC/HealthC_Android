package com.example.healthc.data.source.food.object_detect

import android.util.Base64
import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.domain.utils.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class SearchCategoryDataSourceImpl @Inject constructor(
    private val service : SearchFoodService
) : SearchCategoryDataSource{
    override suspend fun searchFoodCategory(encodedImage: String): Resource<SearchFoodCategory> {
        return try{
            val image: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val requestFile: RequestBody = image
                .toRequestBody("image/*".toMediaTypeOrNull())
            val result = service.searchFoodCategory(
                file = MultipartBody.Part.createFormData(
                    "file",
                    "image.jpg",
                    requestFile
                )
            ).toSearchFoodCategory()
            Resource.Success(result)
        } catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}