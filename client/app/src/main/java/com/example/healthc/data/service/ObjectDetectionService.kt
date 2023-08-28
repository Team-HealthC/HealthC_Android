package com.example.healthc.data.service

import com.example.healthc.BuildConfig
import com.example.healthc.data.model.remote.detection.ObjectDetectionResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ObjectDetectionService {

    // 음식 사진 인식
    @Multipart
    @POST("/food/images/classify")
    suspend fun getDetectedObject(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Part file: MultipartBody.Part,
    ) : ObjectDetectionResponse
}