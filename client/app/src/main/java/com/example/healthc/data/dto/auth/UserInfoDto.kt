package com.example.healthc.data.dto.auth

import com.example.healthc.domain.model.auth.UserInfo

data class UserInfoDto (
    val name: String,
    val disease: List<String>,
    val allergy: List<String>,
){
    // firebase constructor
    constructor(): this("", emptyList(), emptyList())

    // to domain model
    fun toUserInfo() : UserInfo {
        return UserInfo(
            name = name,
            disease = disease,
            allergy = allergy
        )
    }
}