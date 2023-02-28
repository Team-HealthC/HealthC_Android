package com.example.healthc.data.source.local.user

import com.example.healthc.data.local.entity.UserInfoEntity
import com.example.healthc.domain.utils.Resource

interface UpdateLocalUserInfoDataSource {
    suspend fun updateUserInfo(userInfo: UserInfoEntity) : Resource<Unit>
}