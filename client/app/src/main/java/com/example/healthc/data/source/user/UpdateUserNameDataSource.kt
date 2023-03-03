package com.example.healthc.data.source.user

import com.example.healthc.domain.utils.Resource

interface UpdateUserNameDataSource {
    suspend fun updateUserName(uid: String, userName: String) : Resource<Unit>
}