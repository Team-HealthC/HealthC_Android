package com.example.healthc.data.source.local.user

import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.local.UserInfoDao
import com.example.healthc.data.utils.CollectionsUtil
import com.example.healthc.data.utils.await
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class InsertUserInfoDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val dao : UserInfoDao
) : InsertUserInfoDataSource{

    override suspend fun insertUserInfo(uid: String) {
        try{
            val userInfo = fireStore.collection(CollectionsUtil.DB_USERS).document(uid).get().await()
                .toObject(UserInfoDto::class.java)

            dao.deleteUserInfo(uid)
            dao.insertUserInfo(requireNotNull(userInfo).toUserInfoEntity())
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}