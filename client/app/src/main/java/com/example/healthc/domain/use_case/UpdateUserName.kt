package com.example.healthc.domain.use_case

import com.example.healthc.domain.repository.UserRepository
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UpdateUserName @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) {
    suspend operator fun invoke(userName : String) : Resource<Unit> {
        return try{
            val result = userRepository.updateUserName(
                requireNotNull(firebaseAuth.uid),
                userName
            )
           result
        } catch(e: Exception){
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}