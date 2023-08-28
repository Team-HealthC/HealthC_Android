package com.example.healthc.domain.usecase.detection

import com.example.healthc.domain.model.recipe.Ingredient
import com.example.healthc.domain.repository.DetectionRepository
import com.example.healthc.domain.repository.RecipeRepository
import com.example.healthc.domain.repository.UserRepository
import javax.inject.Inject

class CheckAllergiesInImageUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository
){
    suspend operator fun invoke(detectedObject: String): Result<List<String>> {
        return runCatching {
            val ingredientList = recipeRepository.getIngredientList(detectedObject).getOrThrow()
            val user = userRepository.getUser().getOrThrow()

            val detectedAllergies = mutableListOf<String>()
            user.allergies.forEach { allergy ->
                val ingredientNameList = ingredientList.map{ it.name }

                if(ingredientNameList.contains(allergy)){
                    detectedAllergies.add(allergy)
                }
            }
            detectedAllergies
        }
    }
}