package com.healthc.domain.model.product

data class Product(
    val id: Int,
    val imageType: String,
    val name: String,
    val allergiesFree : String,
    val ingredients: String,
    val nutrients: String
){
    constructor() : this(0,"", "", "", "", "")
}
