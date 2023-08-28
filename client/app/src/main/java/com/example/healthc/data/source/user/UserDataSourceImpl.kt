package com.example.healthc.data.source.user

import com.example.healthc.data.model.remote.auth.UserRequest
import com.example.healthc.data.model.remote.auth.UserResponse
import com.example.healthc.data.utils.DB_USERS
import com.example.healthc.data.utils.await
import com.example.healthc.di.IoDispatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val fireStore : FirebaseFirestore,
    private val fireBaseAuth: FirebaseAuth,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): UserDataSource {
    override suspend fun getUser(): Result<UserResponse>
    = withContext(coroutineDispatcher){
        runCatching {
            val collection = fireStore.collection(DB_USERS)
            val document = collection.document(requireNotNull(fireBaseAuth.uid))

            val response = document.get().await().toObject(UserResponse::class.java)
            checkNotNull(response)
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun updateUser(userRequest: UserRequest): Result<Unit>
    = withContext(coroutineDispatcher){
        runCatching {
            val userInfoMap = mutableMapOf<String, Any>()
            userInfoMap["allergies"] = userRequest.allergies
            userInfoMap["name"] = userRequest.name

            val collection = fireStore.collection(DB_USERS)
            val document = collection.document(requireNotNull(fireBaseAuth.uid))

            document.set(userInfoMap, SetOptions.merge()).await()
            Unit
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun updateUserName(userName: String): Result<Unit>
    = withContext(coroutineDispatcher){
        runCatching {
            val collection = fireStore.collection(DB_USERS)
            val document = collection.document(requireNotNull(fireBaseAuth.uid))

            document.update("name", userName).await()
            Unit
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}