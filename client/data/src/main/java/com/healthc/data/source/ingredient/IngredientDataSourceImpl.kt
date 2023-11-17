package com.healthc.data.source.ingredient

import com.google.firebase.firestore.FirebaseFirestore
import com.healthc.data.model.remote.ingredient.FirebaseIngredientResponse
import com.healthc.data.utils.DB_INGREDIENTS
import com.healthc.data.utils.await
import javax.inject.Inject

class IngredientDataSourceImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : IngredientDataSource {
    override suspend fun getIngredientList(
        detectedObject: String
    ): Result<FirebaseIngredientResponse> {
        return runCatching {
            val result = firebaseFirestore.collection(DB_INGREDIENTS)
                .document(detectedObject)
                .get()
                .await()

            val ingredientResponse = result.toObject(FirebaseIngredientResponse::class.java)
            checkNotNull(ingredientResponse)
        }
    }
}