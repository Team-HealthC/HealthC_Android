package com.example.healthc.data.local.entity

import androidx.room.PrimaryKey
import androidx.room.Entity
import com.example.healthc.domain.model.auth.UserInfo

@Entity(
    tableName = "USER_INFO"
)
data class UserInfoEntity(
    val name: String,
    val allergy: List<String>,
    @PrimaryKey val id: Int? = null
) {
    constructor(): this("", emptyList())

    // to domain model
    fun toUserInfo(): UserInfo {
        return UserInfo(
            name = name,
            allergy = allergy
        )
    }
}