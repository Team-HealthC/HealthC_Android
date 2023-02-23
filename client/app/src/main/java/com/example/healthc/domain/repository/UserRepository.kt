package com.example.healthc.domain.repository

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource

interface UserRepository {

    // FireStore data
    suspend fun getUserInfo(uid: String) : Resource<UserInfo>

    // save data to local (sign up, sign in) TODO RETURN VALUE
    suspend fun insertUserInfo(uid: String)

}