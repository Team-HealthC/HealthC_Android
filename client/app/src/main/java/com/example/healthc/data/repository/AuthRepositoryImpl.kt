package com.example.healthc.data.repository

import com.example.healthc.data.dto.auth.UserDto
import com.example.healthc.data.dto.auth.UserInfoDto
import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.domain.repository.AuthRepository
import com.example.healthc.domain.model.auth.User
import com.example.healthc.domain.model.auth.UserInfo
import com.example.healthc.domain.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
    private val signUpDataSource: SignUpDataSource,
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signIn(user: User): Resource<FirebaseUser> =
        signInDataSource.signIn(UserDto(user.email, user.password))

    override suspend fun signUp(user: User, userInfo: UserInfo): Resource<FirebaseUser> =
        signUpDataSource.signUp(UserDto(user.email, user.password),
            UserInfoDto(userInfo.name, userInfo.disease, userInfo.allergy)
        )

    override fun signOut() {
        firebaseAuth.signOut()
    }
}