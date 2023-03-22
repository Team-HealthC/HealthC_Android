package com.example.healthc.domain.model.auth

import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.local.entity.UserInfoEntity

data class UserInfo (
    val name: String,
    val allergy: List<String>,
){
    constructor() : this("", emptyList())

    // to Local Entity
    fun toUserInfoEntity() : UserInfoEntity =
        UserInfoEntity(
            name = name,
            allergy = allergy
        )
}
