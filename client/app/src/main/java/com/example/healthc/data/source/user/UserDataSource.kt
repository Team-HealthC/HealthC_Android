package com.example.healthc.data.source.user

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource

interface UserDataSource {
    suspend fun getUserInfo(uid: String) : Resource<UserInfo>

    suspend fun updateUserInfo(uid : String, userInfo : UserInfo) : Resource<Unit>

    suspend fun updateUserName(uid: String, userName: String) : Resource<Unit>
}