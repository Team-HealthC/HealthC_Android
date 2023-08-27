package com.example.healthc.domain.model.auth

data class User(
    val name: String,
    val allergies: List<String>,
){
    constructor() : this("", emptyList())
}
