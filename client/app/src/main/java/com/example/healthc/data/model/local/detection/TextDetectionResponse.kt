package com.example.healthc.data.model.local.detection

import com.example.healthc.domain.model.detection.TextDetection

data class TextDetectionResponse(
    val recognizedText: String
){
    fun toDomain() = TextDetection(recognizedText = recognizedText)
}