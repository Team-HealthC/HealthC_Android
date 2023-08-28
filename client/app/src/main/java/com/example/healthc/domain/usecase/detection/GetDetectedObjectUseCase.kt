package com.example.healthc.domain.usecase.detection

import com.example.healthc.domain.model.detection.ObjectDetection
import com.example.healthc.domain.repository.DetectionRepository
import javax.inject.Inject

class GetDetectedObjectUseCase @Inject constructor(
    private val objectRepository: DetectionRepository
){
    suspend operator fun invoke(image: ByteArray): Result<ObjectDetection>{
        return objectRepository.getDetectedObject(image)
    }
}