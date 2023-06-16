package com.example.healthc.data.remote.api

import com.example.healthc.BuildConfig
import com.example.healthc.data.dto.object_detection.DetectedObjectDto
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ObjectDetectionService {

    // 음식 사진 인식
    @Multipart
    @POST("/food/images/classify")
    suspend fun postFoodImage(
        @Query("apiKey") apiKey : String = BuildConfig.SPOON_API_KEY,
        @Part file: MultipartBody.Part,
    ) : DetectedObjectDto
}