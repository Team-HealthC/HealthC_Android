package com.healthc.domain.model.detection

// 오직 OCR, 문자 인식만을 위한 도메인 모델
data class TextDetection(
    val recognizedText: String
)