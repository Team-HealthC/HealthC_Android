package com.example.healthc.data.source.user

import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.utils.CollectionsUtil
import com.example.healthc.data.utils.CollectionsUtil.Companion.DB_USERS
import com.example.healthc.data.utils.await
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val fireStore : FirebaseFirestore,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): UserDataSource{
    override suspend fun getUserInfo(uid: String): Resource<UserInfo>
    = withContext(coroutineDispatcher){
        try{
            val result = fireStore.collection(DB_USERS).document(uid).get().await()
                .toObject(UserInfoDto::class.java)
            Resource.Success(
                requireNotNull(result).toUserInfo()
            )
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateUserInfo(uid: String, userInfo: UserInfo): Resource<Unit>
            = withContext(coroutineDispatcher){
        try{
            val userInfoMap = mutableMapOf<String, Any>()
            userInfoMap["allergy"] = userInfo.allergy
            userInfoMap["name"] = userInfo.name

            fireStore.collection(DB_USERS).document(uid).set(
                userInfoMap,
                SetOptions.merge()
            ).await()
            Resource.Success(Unit)
        }
        catch (e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateUserName(uid: String, userName: String): Resource<Unit>
            = withContext(coroutineDispatcher){
        try{
            val result = fireStore.collection(CollectionsUtil.DB_USERS).document(uid).update(
                "name",
                userName
            ).await()
            Resource.Success(Unit)
        }
        catch (e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}