package com.example.healthc.data.dto.auth

import com.example.healthc.data.local.entity.UserInfoEntity
import com.example.healthc.domain.model.auth.UserInfo

data class UserInfoDto (
    val name: String,
    val allergy: List<String>,
){
    // firebase constructor
    constructor(): this("", emptyList())

    // to domain model
    fun toUserInfo() : UserInfo {
        return UserInfo(
            name = name,
            allergy = allergy
        )
    }
}