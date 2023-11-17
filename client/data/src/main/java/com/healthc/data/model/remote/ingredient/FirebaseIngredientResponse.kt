package com.healthc.data.model.remote.ingredient

data class FirebaseIngredientResponse(
    val ingredientList: List<String>
) {
    constructor(): this(
        ingredientList = emptyList()
    )
}
