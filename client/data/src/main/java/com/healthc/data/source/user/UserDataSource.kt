package com.healthc.data.source.user

import com.healthc.data.model.remote.auth.UserRequest
import com.healthc.data.model.remote.auth.UserResponse

interface UserDataSource {
    suspend fun getUser() : Result<UserResponse>

    suspend fun updateUser(userRequest : UserRequest) : Result<Unit>

    suspend fun updateUserName(userName: String) : Result<Unit>
}