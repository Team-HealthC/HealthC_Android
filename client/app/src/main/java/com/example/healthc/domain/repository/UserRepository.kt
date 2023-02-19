package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource

interface UserRepository {
    suspend fun getUserInfo(uid: String) : Resource<UserInfo>
}