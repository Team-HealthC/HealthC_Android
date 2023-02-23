package com.example.healthc.data.source.auth

import com.example.healthc.data.dto.auth.UserDto
import com.example.healthc.data.dto.auth.UserInfoDto
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
        userDto: UserDto,
        userInfoDto: UserInfoDto,
    ): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(
                userDto.email, userDto.password
            ).await()
            if(result.user != null){
                // 사용자 정보 DB에 입력
                fireStore.collection(DB_USERS).document(
                    requireNotNull(result.user).uid
                ).set(userInfoDto).await()
            }
            Resource.Success(checkNotNull(result.user))
        }
        catch(e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}