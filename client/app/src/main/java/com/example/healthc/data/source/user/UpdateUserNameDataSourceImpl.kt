package com.example.healthc.data.source.user

import com.example.healthc.data.utils.CollectionsUtil
import com.example.healthc.data.utils.await
import com.example.healthc.domain.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class UpdateUserNameDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : UpdateUserNameDataSource{
    override suspend fun updateUserName(uid: String, userName: String): Resource<Unit> {
        return try{
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