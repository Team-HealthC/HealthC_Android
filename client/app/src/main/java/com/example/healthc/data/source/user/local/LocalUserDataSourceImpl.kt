package com.example.healthc.data.source.user.local

import com.example.healthc.data.local.UserInfoDao
import com.example.healthc.data.local.entity.UserInfoEntity
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(
    private val dao : UserInfoDao
) : LocalUserDataSource {
    override fun getUserInfo(): Flow<Resource<UserInfo>> =
        dao.getUserInfo()
            .map { Resource.Success(it.toUserInfo()) }
            // .catch { exception -> emit(Resource.Failure(Exception(exception))) }

    override suspend fun updateUserInfo(userInfo: UserInfoEntity) : Resource<Unit>{
        return try{
            dao.deleteUserInfo(userInfo)
            dao.insertUserInfo(userInfo)
            Resource.Success(Unit)
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}