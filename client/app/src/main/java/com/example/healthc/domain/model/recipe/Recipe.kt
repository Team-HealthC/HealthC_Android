package com.example.healthc.domain.model.recipe

data class Recipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val likes: Int,
    val title: String
)
