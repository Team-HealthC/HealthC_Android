package com.example.healthc.domain.usecase.detection

import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.utils.toIngredientEng
import javax.inject.Inject

class CheckAllergiesInTextUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(recognizedText: String, isEnglish: Boolean) : Result<List<String>> {
        return runCatching {
            val user = userRepository.getUser().getOrThrow()
            if (isEnglish) { // 영어 OCR인 경우 변역됨.
                user.allergies.map { allergy ->
                    allergy.toIngredientEng()
                }
            }

            val detectedAllergies = mutableListOf<String>()
            user.allergies.forEach { allergy ->
                if (recognizedText.contains(allergy)) {
                    detectedAllergies.add(allergy)
                }
            }
            detectedAllergies
        }
    }
}