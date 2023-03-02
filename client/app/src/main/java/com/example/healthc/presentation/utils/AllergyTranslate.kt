package com.example.healthc.presentation.utils

fun String.toEng(): String =
    when(this){
        "꿀" -> { "honey" }

        "대두" -> { "bean" }

        "땅콩" -> { "peanut" }

        "계란" -> { "egg" }

        "우유" -> { "milk" }

        "밀" -> { "wheat" }

        "복숭아" -> { "peach" }

        "석류" -> { "Pomegranate" }

        else -> { "egg" }
    }