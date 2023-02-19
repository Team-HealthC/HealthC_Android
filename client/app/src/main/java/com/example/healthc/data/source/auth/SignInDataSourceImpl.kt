package com.example.healthc.data.source.auth

import com.example.healthc.data.entity.auth.UserEntity
import com.example.healthc.domain.utils.Resource
import com.example.healthc.presentation.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): SignInDataSource {
    override suspend fun signIn(userEntity: UserEntity): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.signInWithEmailAndPassword(
                userEntity.email, userEntity.password
            ).await()
            Resource.Success(checkNotNull(result.user))
        }
        catch(e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}
