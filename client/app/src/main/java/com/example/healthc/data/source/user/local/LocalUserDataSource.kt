package com.example.healthc.data.source.user.local

import com.example.healthc.data.local.entity.UserInfoEntity
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    fun getUserInfo() : Flow<Resource<UserInfo>>

    suspend fun updateUserInfo(userInfo: UserInfoEntity) : Resource<Unit>

}