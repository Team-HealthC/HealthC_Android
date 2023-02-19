package com.example.healthc.di

import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignInDataSourceImpl
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.data.source.auth.SignUpDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideSignUpDataSource(
        signUpDataSourceImpl: SignUpDataSourceImpl
    ): SignUpDataSource

    @Binds
    @Singleton
    abstract fun provideSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource

}