package com.example.healthc.data.source.detection

import com.example.healthc.data.model.remote.detection.ObjectDetectionResponse
import com.example.healthc.data.service.ObjectDetectionService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class DetectionDataSourceImpl @Inject constructor(
    private val service : ObjectDetectionService
) : DetectionDataSource {
    override suspend fun getDetectedObject(image: ByteArray): Result<ObjectDetectionResponse>{
        return runCatching {
            val requestFile = image.toRequestBody("image/*".toMediaTypeOrNull())
            service.getDetectedObject(
                file = MultipartBody.Part.createFormData(
                    name = "file",
                    filename = "image.jpg",
                    requestFile
                )
            )
        }
    }
}