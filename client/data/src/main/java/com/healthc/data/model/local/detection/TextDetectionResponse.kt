package com.healthc.data.model.local.detection

import com.healthc.domain.model.detection.TextDetection

data class TextDetectionResponse(
    val recognizedText: String
){
    fun toDomain() = TextDetection(recognizedText = recognizedText)
}