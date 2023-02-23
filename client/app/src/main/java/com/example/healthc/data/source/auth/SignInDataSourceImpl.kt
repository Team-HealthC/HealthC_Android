package com.example.healthc.data.source.auth

import com.example.healthc.data.dto.auth.UserDto
import com.example.healthc.domain.utils.Resource
import com.example.healthc.data.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): SignInDataSource {
    override suspend fun signIn(userDto: UserDto): Resource<FirebaseUser> {
        return try{
            val result = firebaseAuth.signInWithEmailAndPassword(
                userDto.email, userDto.password
            ).await()
            Resource.Success(checkNotNull(result.user))
        }
        catch(e : Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}
