package com.example.healthc.data.model.remote.auth

import com.example.healthc.domain.model.auth.Allergy
import com.example.healthc.domain.model.auth.User

data class UserResponse(
    val name: String,
    val allergies: List<String>
){

    fun toDomain() = User(
        name = name,
        allergies = allergies.map{ Allergy(it) }
    )
}