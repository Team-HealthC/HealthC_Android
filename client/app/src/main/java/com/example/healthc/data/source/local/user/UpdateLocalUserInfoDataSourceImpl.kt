package com.example.healthc.data.source.local.user

import com.example.healthc.data.local.UserInfoDao
import com.example.healthc.data.local.entity.UserInfoEntity
import com.example.healthc.domain.utils.Resource
import javax.inject.Inject

class UpdateLocalUserInfoDataSourceImpl @Inject constructor(
    private val dao : UserInfoDao
) : UpdateLocalUserInfoDataSource{

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