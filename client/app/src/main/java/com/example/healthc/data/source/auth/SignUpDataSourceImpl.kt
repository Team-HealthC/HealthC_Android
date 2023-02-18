package com.example.healthc.data.source.auth

import com.example.healthc.data.entity.auth.UserEntity
import com.example.healthc.data.entity.auth.UserInfoEntity
import com.example.healthc.domain.utils.Resource
import com.example.healthc.presentation.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): SignUpDataSource{
    override suspend fun signUp(
        userEntity: UserEntity,
        userInfoEntity: UserInfoEntity,
    ): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(
                userEntity.email, userEntity.password
            ).await()
            // TODO firebase User INFO 등록
            Resource.Success(checkNotNull(result.user))
        }
        catch(e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}