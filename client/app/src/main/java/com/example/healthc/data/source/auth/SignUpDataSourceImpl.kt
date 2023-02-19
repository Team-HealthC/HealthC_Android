package com.example.healthc.data.source.auth

import com.example.healthc.data.entity.auth.UserEntity
import com.example.healthc.data.entity.auth.UserInfoEntity
import com.example.healthc.data.utils.CollectionsUtil.Companion.DB_USERS
import com.example.healthc.domain.utils.Resource
import com.example.healthc.data.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
): SignUpDataSource{
    override suspend fun signUp(
        userEntity: UserEntity,
        userInfoEntity: UserInfoEntity,
    ): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(
                userEntity.email, userEntity.password
            ).await()
            if(result.user != null){
                // 사용자 정보 DB에 입력
                fireStore.collection(DB_USERS).document(
                    requireNotNull(result.user).uid
                ).set(userInfoEntity).await()
            }
            Resource.Success(checkNotNull(result.user))
        }
        catch(e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}