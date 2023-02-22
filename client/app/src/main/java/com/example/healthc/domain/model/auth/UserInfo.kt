package com.example.healthc.domain.model.auth

data class UserInfo (
    val name: String,
    val disease: List<String>,
    val allergy: List<String>,
){
    constructor() : this("", emptyList(), emptyList())
}