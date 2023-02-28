package com.example.healthc.data.source.local.user

import com.example.healthc.data.local.UserInfoDao
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetLocalUserInfoDataSourceImpl @Inject constructor(
    private val dao : UserInfoDao
) : GetLocalUserInfoDataSource{
    override fun getUserInfo(): Flow<Resource<UserInfo>> =
        dao.getUserInfo()
            .map { Resource.Success(it.toUserInfo()) }
            // .catch { exception -> emit(Resource.Failure(Exception(exception))) }
}