package com.example.healthc.data.repository

import com.example.healthc.data.source.user.local.LocalUserDataSource
import com.example.healthc.data.source.user.UserDataSource
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
    private val localUserDataSource: LocalUserDataSource
) : UserRepository{
    override suspend fun getUserInfo(uid: String): Resource<UserInfo> {
        return userDataSource.getUserInfo(uid)
    }

    override suspend fun updateUserInfo(uid : String, userInfo : UserInfo) : Resource<Unit> {
        return userDataSource.updateUserInfo(uid, userInfo)
    }

    override suspend fun updateUserName(uid: String, userName: String): Resource<Unit> {
        return userDataSource.updateUserName(uid, userName)
    }

    override fun getLocalUserInfo(): Flow<Resource<UserInfo>> {
        return localUserDataSource.getUserInfo()
    }

    override suspend fun updateLocalUserInfo(userInfo : UserInfo): Resource<Unit> {
        return localUserDataSource.updateUserInfo(userInfo.toUserInfoEntity())
    }
}