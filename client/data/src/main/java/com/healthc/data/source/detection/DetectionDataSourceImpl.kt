package com.healthc.data.source.detection

import android.content.Context
import androidx.core.net.toUri
import com.healthc.data.model.local.detection.TextDetectionResponse
import com.healthc.data.model.remote.detection.ObjectDetectionResponse
import com.healthc.data.service.ObjectDetectionService
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import com.healthc.data.di.EnglishRecognizer
import com.healthc.data.di.IoDispatcher
import com.healthc.data.di.KoreanRecognizer
import com.healthc.data.utils.await
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class DetectionDataSourceImpl @Inject constructor(
    private val service : ObjectDetectionService,
    @ApplicationContext private val context: Context,
    @KoreanRecognizer private val koreanRecognizer: TextRecognizer,
    @EnglishRecognizer private val englishRecognizer: TextRecognizer,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
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

    override suspend fun getDetectedKoreanText(imageUri: String): Result<TextDetectionResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            val image = InputImage.fromFilePath(context, imageUri.toUri())
            val response = koreanRecognizer.process(image).await()
            TextDetectionResponse(
                response.text.replace("\n", "")
            )
        }
    }

    override suspend fun getDetectedEnglishText(imageUri: String): Result<TextDetectionResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            val image = InputImage.fromFilePath(context, imageUri.toUri())
            val response = englishRecognizer.process(image).await()
            TextDetectionResponse(
                response.text.replace("\n", "")
            )
        }
    }
}