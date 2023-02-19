package com.example.healthc.di

import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignInDataSourceImpl
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.data.source.auth.SignUpDataSourceImpl
import com.example.healthc.data.source.user.GetUserInfoDataSource
import com.example.healthc.data.source.user.GetUserInfoDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun providesGetUserInfoDataSource(
        getUserInfoDataSourceImpl: GetUserInfoDataSourceImpl
    ): GetUserInfoDataSource

}
