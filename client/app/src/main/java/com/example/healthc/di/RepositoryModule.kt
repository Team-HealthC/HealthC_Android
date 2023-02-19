package com.example.healthc.di

import com.example.healthc.data.repository.AuthRepositoryImpl
import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesAuthRepository(signInDataSource: SignInDataSource,
                               signUpDataSource: SignUpDataSource,
                               firebaseAuth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(signInDataSource, signUpDataSource, firebaseAuth)
}