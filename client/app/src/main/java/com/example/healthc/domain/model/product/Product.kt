package com.example.healthc.domain.model.product

data class Product(
    val id: Int,
    val ingredientList: String,
    val likes: Int,
    val nutrients: List<String>,
    val title: String
){
    constructor() : this(0, "", 0, emptyList(),"")
}
