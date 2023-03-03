package com.example.healthc.presentation.utils

fun String.toEng(): String =
    when(this){
        "꿀" -> { "Honey" }

        "대두" -> { "Bean" }

        "땅콩" -> { "Peanut" }

        "계란" -> { "Egg" }

        "우유" -> { "Milk" }

        "밀" -> { "Wheat" }

        "복숭아" -> { "Peach" }

        "석류" -> { "Pomegranate" }

        "게" -> { "Crab"}

        else -> { "egg" }
    }