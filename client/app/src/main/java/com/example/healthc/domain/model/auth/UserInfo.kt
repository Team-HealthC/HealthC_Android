package com.example.healthc.domain.model.auth

import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.local.entity.UserInfoEntity

data class UserInfo (
    val name: String,
    val disease: List<String>,
    val allergy: List<String>,
){
    constructor() : this("", emptyList(), emptyList())

    // to Local Entity
    fun toUserInfoEntity() : UserInfoEntity =
        UserInfoEntity(
            name = name,
            disease = disease,
            allergy = allergy
        )

    // to Remote Dto
    fun toUserInfoDto() : UserInfoDto =
        UserInfoDto(
            name = name,
            disease = disease,
            allergy = allergy
        )
}