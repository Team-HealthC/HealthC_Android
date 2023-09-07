package com.healthc.data.model.remote.auth

import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.model.auth.User

data class UserResponse(
    val name: String,
    val allergies: List<String>
){
    constructor(): this(
        "",
        emptyList()
    )


    fun toDomain() = User(
        name = name,
        allergies = allergies.map{ Allergy(it) }
    )
}