package com.example.healthc.data.dto.food.product

import com.example.healthc.domain.model.food.ProductNutrient
import com.example.healthc.domain.model.food.ProductNutrition
import com.example.healthc.domain.model.food.SearchProductInfo

data class SearchProductInfoDto(
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
    fun toSearchProductInfo(): SearchProductInfo = SearchProductInfo(
        id = id,
        ingredientList = ingredientList,
        likes = likes,
        title = title,
        nutrition = nutrition.toProductNutrition()
    )
}

data class ProductServingsDto(
    val number: Int,
    val size: Int,
    val unit: String
)

data class ProductNutritionDto(
    val caloricBreakdown: ProductCaloricBreakdownDto,
    val nutrients: List<ProductNutrientDto>
){
    fun toProductNutrition() : ProductNutrition =  ProductNutrition(
        nutrients = nutrients.map{ it.toProductNutrient() }
    )
}

data class ProductNutrientDto(
    val amount: Int,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
){
    fun toProductNutrient() : ProductNutrient = ProductNutrient(
        amount = amount,
        name = name,
        unit = unit
    )
}

data class ProductIngredientDto(
    val description: String,
    val name: String,
    val safety_level: String
)

data class ProductCaloricBreakdownDto(
    val percentCarbs: Double,
    val percentFat: Int,
    val percentProtein: Double
)