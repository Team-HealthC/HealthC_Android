package com.example.healthc.data.source.user

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource

interface UpdateUserInfoDataSource {
    suspend fun updateUserInfo(uid : String, userInfo : UserInfo) : Resource<Unit>
}