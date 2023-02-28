package com.example.healthc.data.source.local.user

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetLocalUserInfoDataSource {
    fun getUserInfo() : Flow<Resource<UserInfo>>
}