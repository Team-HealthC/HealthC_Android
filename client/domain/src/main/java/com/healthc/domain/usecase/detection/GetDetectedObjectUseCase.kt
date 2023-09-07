

package com.healthc.domain.usecase.detection

import com.healthc.domain.model.detection.ObjectDetection
import com.healthc.domain.repository.DetectionRepository
import javax.inject.Inject

class GetDetectedObjectUseCase @Inject constructor(
    private val objectRepository: DetectionRepository
){
    suspend operator fun invoke(image: ByteArray): Result<ObjectDetection>{
        return objectRepository.getDetectedObject(image)
    }
}