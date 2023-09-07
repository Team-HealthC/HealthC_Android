package com.healthc.data.source.auth

import com.healthc.data.utils.DB_USERS
import com.healthc.data.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.healthc.data.di.IoDispatcher
import com.healthc.data.model.remote.auth.UserAccountRequest
import com.healthc.data.model.remote.auth.UserRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): AuthDataSource{
    override suspend fun signIn(userAccountRequest: UserAccountRequest): Result<Unit>
    = withContext(coroutineDispatcher){
        runCatching {
            firebaseAuth.signInWithEmailAndPassword(
                userAccountRequest.email, userAccountRequest.password
            ).await()
            Unit
        }.onFailure {  e ->
            e.printStackTrace()
        }
    }

    override suspend fun signUp(
        userAccountRequest: UserAccountRequest,
        userRequest: UserRequest,
    ): Result<Unit> = withContext(coroutineDispatcher){
        runCatching {
            val result = firebaseAuth.createUserWithEmailAndPassword(
                userAccountRequest.email, userAccountRequest.password
            ).await()

            val user = checkNotNull(result.user)
            val collection = fireStore.collection(DB_USERS)
            val document = collection.document(user.uid)

            document.set(userRequest).await()
            Unit
        }.onFailure { e ->
            e.printStackTrace()
        }
    }

    override suspend fun signOut(): Result<Unit> = withContext(coroutineDispatcher){
        runCatching {
            firebaseAuth.signOut()
        }.onFailure { e ->
            e.printStackTrace()
        }
    }
}