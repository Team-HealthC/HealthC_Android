package com.example.healthc.data.dto.product

import com.example.healthc.domain.model.product.Product

data class ProductDto(
    val aisle: String,
    val badges: List<String>,
    val breadcrumbs: List<String>,
    val generatedText: Any,
    val id: Int,
    val imageType: String,
    val importantBadges: List<String>,
    val ingredientCount: Int,
    val ingredientList: String,
    val ingredients: List<ProductIngredientDto>,
    val likes: Int,
    val nutrition: ProductNutritionDto,
    val price: Double,
    val servings: ProductServingsDto,
    val spoonacularScore: Double,
    val title: String
){
    fun toProduct(): Product = Product(
        id = id,
        ingredientList = ingredientList,
        likes = likes,
        title = title,
        nutrients = nutrition.nutrients.map{ it.name }
    )
}

data class ProductServingsDto(
    val number: Int,
    val size: Int?,
    val unit: String
)

data class ProductNutritionDto(
    val caloricBreakdown: ProductCaloricBreakdownDto,
    val nutrients: List<ProductNutrientDto>
)

data class ProductNutrientDto(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)

data class ProductIngredientDto(
    val description: String,
    val name: String,
    val safety_level: String
)

data class ProductCaloricBreakdownDto(
    val percentCarbs: Double,
    val percentFat: Double,
    val percentProtein: Double
)