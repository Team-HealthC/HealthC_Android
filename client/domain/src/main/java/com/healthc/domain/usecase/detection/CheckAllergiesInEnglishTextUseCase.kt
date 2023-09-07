package com.healthc.domain.usecase.detection

import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.repository.DetectionRepository
import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class CheckAllergiesInEnglishTextUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
){
    suspend operator fun invoke(imageUri: String) : Result<List<Allergy>> {
        return runCatching {
            val response = userRepository.getUser().getOrThrow()
            val userAllergies = response.allergies.map { allergy ->
                allergy.toEnglish()
            }

            val recognizedText =
                detectionRepository.getDetectedEnglishText(imageUri).getOrThrow().toString()

            val detectedAllergies = mutableListOf<Allergy>()
            userAllergies.forEach { allergy ->
                if (recognizedText.contains(allergy.allergy)) {
                    detectedAllergies.add(allergy)
                }
            }
            detectedAllergies
        }
    }
}