package com.healthc.data.model.remote.product

import com.healthc.domain.model.product.Product
import com.squareup.moshi.Json

data class ProductResponse(
    val aisle: String,
    val badges: List<String>,
    val breadcrumbs: List<String>,
    val generatedText: Any,
    val id: Int,
    val imageType: String,
    val importantBadges: List<String>,
    val ingredientCount: Int,
    val ingredientList: String,
    val ingredients: List<ProductIngredientResponse>,
    val likes: Int,
    val nutrition: ProductNutritionResponse,
    val price: Double,
    val servings: ProductServingResponse,
    val spoonacularScore: Double,
    val title: String
){
    fun toDomain(): Product = Product(
        id = id,
        imageType = imageType,
        name = title,
        allergiesFree = badges.joinToString(", "),
        ingredients = ingredientList,
        nutrients = nutrition.nutrients.joinToString(", ") { it.name }
    )
}

data class ProductServingResponse(
    val number: Int,
    val size: Double?,
    val unit: String
)

data class ProductNutritionResponse(
    val caloricBreakdown: ProductCaloricBreakdownResponse,
    val nutrients: List<ProductNutrientResponse>
)

data class ProductNutrientResponse(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)

data class ProductIngredientResponse(
    val description: String,
    val name: String,
    @Json(name = "safety_level") val safetyLevel: String
)

data class ProductCaloricBreakdownResponse(
    val percentCarbs: Double,
    val percentFat: Double,
    val percentProtein: Double
)