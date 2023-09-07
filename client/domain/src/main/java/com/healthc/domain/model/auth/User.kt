package com.healthc.domain.model.auth

data class User(
    val name: String,
    val allergies: List<Allergy>,
){
    constructor() : this("", emptyList())
}
