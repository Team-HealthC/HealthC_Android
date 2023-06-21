package com.example.healthc.utils

fun String.toIngredientEng(): String =
    when(this){
        "돼지고기" -> { "pork" }

        "대두" -> { "soybean" }

        "땅콩" -> { "peanut" }

        "계란" -> { "egg" }

        "우유" -> { "milk" }

        "밀" -> { "flour" }

        "복숭아" -> { "peach" }

        "견과류" -> { "nuts" }

        "갑각류" -> { "crab"}

        "조개류" -> { "clam"}

        "생선" -> { "fish"}

        else -> { "" }
    }