package com.example.healthc.data.repository

import com.example.healthc.data.source.local.user.GetLocalUserInfoDataSource
import com.example.healthc.data.source.local.user.UpdateLocalUserInfoDataSource
import com.example.healthc.data.source.user.GetUserInfoDataSource
import com.example.healthc.data.source.user.UpdateUserInfoDataSource
import com.example.healthc.data.source.user.UpdateUserNameDataSource
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val getUserInfoDataSource: GetUserInfoDataSource,
    private val updateUserInfoDataSource: UpdateUserInfoDataSource,
    private val updateUserNameDataSource: UpdateUserNameDataSource,
    private val updateLocalUserInfoDataSource: UpdateLocalUserInfoDataSource,
    private val getUserLocalDataSource: GetLocalUserInfoDataSource
) : UserRepository{
    override suspend fun getUserInfo(uid: String): Resource<UserInfo> {
        return getUserInfoDataSource.getUserInfo(uid)
    }

    override suspend fun updateUserInfo(uid : String, userInfo : UserInfo) : Resource<Unit> {
        return updateUserInfoDataSource.updateUserInfo(uid, userInfo)
    }

    override suspend fun updateUserName(uid: String, userName: String): Resource<Unit> {
        return updateUserNameDataSource.updateUserName(uid, userName)
    }

    override fun getLocalUserInfo(): Flow<Resource<UserInfo>> {
        return getUserLocalDataSource.getUserInfo()
    }

    override suspend fun updateLocalUserInfo(userInfo : UserInfo): Resource<Unit> {
        return updateLocalUserInfoDataSource.updateUserInfo(userInfo.toUserInfoEntity())
    }
}