package com.healthc.domain.usecase.detection

import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.repository.IngredientRepository
import com.healthc.domain.repository.UserRepository
import javax.inject.Inject

class CheckAllergiesInImageUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
){
    suspend operator fun invoke(detectedObject: String): Result<List<Allergy>> {
        return runCatching {
            val ingredientList = ingredientRepository.getIngredientList(detectedObject).getOrThrow()
            val user = userRepository.getUser().getOrThrow()

            val detectedAllergies = mutableListOf<Allergy>()
            user.allergies.forEach { allergy ->
                if(ingredientList.contains(allergy.allergy)){
                    detectedAllergies.add(allergy)
                }
            }
            detectedAllergies
        }
    }
}