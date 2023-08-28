package com.example.healthc.data.model.remote.auth

import com.example.healthc.domain.model.auth.User

data class UserRequest (
    val name: String,
    val allergies: List<String>,
){
    constructor(): this("", emptyList())

    fun toDomain() = User(
        name = name,
        allergies = allergies
    )
}