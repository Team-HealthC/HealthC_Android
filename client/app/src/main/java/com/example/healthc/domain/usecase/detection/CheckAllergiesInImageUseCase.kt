package com.example.healthc.domain.usecase.detection

import com.example.healthc.domain.model.auth.Allergy
import com.example.healthc.domain.repository.RecipeRepository
import com.example.healthc.domain.repository.UserRepository
import javax.inject.Inject

class CheckAllergiesInImageUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository
){
    suspend operator fun invoke(detectedObject: String): Result<List<Allergy>> {
        return runCatching {
            val ingredientList = recipeRepository.getIngredientList(detectedObject).getOrThrow()
            val user = userRepository.getUser().getOrThrow()

            val detectedAllergies = mutableListOf<Allergy>()
            user.allergies.forEach { allergy ->
                val ingredientNameList = ingredientList.map{ it.name }
                val translatedAllergy = allergy.toEnglish().toString()

                if(ingredientNameList.contains(translatedAllergy)){
                    detectedAllergies.add(allergy)
                }
            }
            detectedAllergies
        }
    }
}