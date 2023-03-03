package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // FireStore RETRIEVE USER_INFO
    suspend fun getUserInfo(uid: String) : Resource<UserInfo>

    // Firestore UPDATE USER_INFO
    suspend fun updateUserInfo(uid: String, userInfo : UserInfo): Resource<Unit>

    // Firestore UPDATE USER_NAME
    suspend fun updateUserName(uid: String, userName: String) : Resource<Unit>

    // save data to local (sign up, sign in)
    suspend fun updateLocalUserInfo(userInfo: UserInfo) : Resource<Unit>

    // Local RETRIEVE USER_INFO
    fun getLocalUserInfo() : Flow<Resource<UserInfo>>

}