package com.example.healthc.data.entity.auth

data class UserInfoEntity (
    val name: String,
    val disease: List<String>,
    val allergy: List<String>,
)