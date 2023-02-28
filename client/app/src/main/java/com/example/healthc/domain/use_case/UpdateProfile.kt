package com.example.healthc.domain.use_case

import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UpdateProfile @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(userInfo : UserInfo) : Resource<Unit> {
        return try{
            val result = userRepository.updateUserInfo(
                requireNotNull(firebaseAuth.uid),
                userInfo
            )
           result
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}