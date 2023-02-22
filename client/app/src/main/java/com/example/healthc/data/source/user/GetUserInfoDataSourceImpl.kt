package com.example.healthc.data.source.user

import com.example.healthc.data.dto.auth.UserInfoEntity
import com.example.healthc.data.utils.CollectionsUtil.Companion.DB_USERS
import com.example.healthc.data.utils.await
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class GetUserInfoDataSourceImpl @Inject constructor(
    private val fireStore : FirebaseFirestore
): GetUserInfoDataSource{
    override suspend fun getUserInfo(uid: String): Resource<UserInfo> {
        return try{
            val result = fireStore.collection(DB_USERS).document(uid).get().await()
                .toObject(UserInfoEntity::class.java)
            Resource.Success(
                requireNotNull(result).toUserInfo()
            )
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}