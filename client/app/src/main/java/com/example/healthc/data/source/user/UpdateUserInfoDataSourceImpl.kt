package com.example.healthc.data.source.user

import com.example.healthc.data.utils.CollectionsUtil.Companion.DB_USERS
import com.example.healthc.data.utils.await
import com.example.healthc.di.IoDispatcher
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateUserInfoDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): UpdateUserInfoDataSource{
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
}