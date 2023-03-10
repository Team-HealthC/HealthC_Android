package com.example.healthc.data.source.user

import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.utils.CollectionsUtil.Companion.DB_USERS
import com.example.healthc.data.utils.await
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserInfoDataSourceImpl @Inject constructor(
    private val fireStore : FirebaseFirestore,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): GetUserInfoDataSource{
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
}