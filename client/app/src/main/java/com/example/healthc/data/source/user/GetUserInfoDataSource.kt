package com.example.healthc.data.source.user

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource

interface GetUserInfoDataSource {
    suspend fun getUserInfo(uid: String) : Resource<UserInfo>
}