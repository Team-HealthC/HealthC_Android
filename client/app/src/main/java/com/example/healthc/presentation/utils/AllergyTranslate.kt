package com.example.healthc.presentation.utils

fun String.toIngredientEng(): String =
    when(this){
        "돼지고기" -> { "Pork" }

        "대두" -> { "Soy" }

        "땅콩" -> { "Peanut" }

        "계란" -> { "Egg" }

        "우유" -> { "Milk" }

        "밀" -> { "Wheat" }

        "복숭아" -> { "Peach" }

        "견과류" -> { "Nuts" }

        "갑각류" -> { "Crab"}

        "조개류" -> { "Shell"}

        "생선" -> { "Fish"}

        else -> { "" }
    }