package com.example.healthc.data.repository

import com.example.healthc.data.source.local.user.InsertUserInfoDataSource
import com.example.healthc.data.source.user.GetUserInfoDataSource
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val getUserInfoDataSource: GetUserInfoDataSource,
    private val insertUserInfoDataSource: InsertUserInfoDataSource
) : UserRepository{
    override suspend fun getUserInfo(uid: String): Resource<UserInfo> {
        return getUserInfoDataSource.getUserInfo(uid)
    }

    override suspend fun insertUserInfo(uid: String) {
        return insertUserInfoDataSource.insertUserInfo(uid)
    }
}