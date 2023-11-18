package com.healthc.domain.usecase.detection

import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.model.detection.AllergyTextDetection
import com.healthc.domain.repository.DetectionRepository
import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class CheckAllergiesInKoreanTextUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
){
    suspend operator fun invoke(imageUri: String) : Result<AllergyTextDetection> {
        return runCatching {
            val user = userRepository.getUser().getOrThrow()

            val recognizedText =
                detectionRepository.getDetectedKoreanText(imageUri).getOrThrow().recognizedText

            val detectedAllergies = mutableListOf<Allergy>()
            user.allergies.forEach { allergy ->
                if (recognizedText.contains(allergy.allergy)) {
                    detectedAllergies.add(allergy)
                }
            }
            AllergyTextDetection(
                allergies = detectedAllergies,
                recognizedText = recognizedText,
            )
        }
    }
}