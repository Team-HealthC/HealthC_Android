package com.example.healthc.data.source.object_detection

import com.example.healthc.data.remote.api.ObjectDetectionService
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.object_detection.DetectedObject
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import javax.inject.Inject


class ObjectDetectionDataSourceImpl @Inject constructor(
    private val service : ObjectDetectionService,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ObjectDetectionDataSource {
    override suspend fun postFoodImage(image: ByteArray): Resource<DetectedObject>
    = withContext(coroutineDispatcher){
        try{
            val requestFile: RequestBody = image
                .toRequestBody("image/*".toMediaTypeOrNull())
            val result = service.postFoodImage(
                file = MultipartBody.Part.createFormData(
                    "file",
                    "image.jpg",
                    requestFile
                )
            ).toDetectedObject()
            Resource.Success(result)
        } catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}