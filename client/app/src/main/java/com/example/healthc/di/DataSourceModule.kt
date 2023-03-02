package com.example.healthc.di

import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignInDataSourceImpl
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.data.source.auth.SignUpDataSourceImpl
import com.example.healthc.data.source.food.SearchIngredientDataSource
import com.example.healthc.data.source.food.SearchIngredientDataSourceImpl
import com.example.healthc.data.source.local.user.GetLocalUserInfoDataSource
import com.example.healthc.data.source.local.user.GetLocalUserInfoDataSourceImpl
import com.example.healthc.data.source.local.user.UpdateLocalUserInfoDataSource
import com.example.healthc.data.source.local.user.UpdateLocalUserInfoDataSourceImpl
import com.example.healthc.data.source.user.GetUserInfoDataSource
import com.example.healthc.data.source.user.GetUserInfoDataSourceImpl
import com.example.healthc.data.source.user.UpdateUserInfoDataSource
import com.example.healthc.data.source.user.UpdateUserInfoDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun providesUpdateUserInfoDataSource(
        updateUserInfoDataSourceImpl: UpdateUserInfoDataSourceImpl
    ) : UpdateUserInfoDataSource

    @Binds
    @Singleton
    abstract fun providesGetLocalUserInfoDataSource(
        getLocalUserInfoDataSourceImpl: GetLocalUserInfoDataSourceImpl
    ): GetLocalUserInfoDataSource

    @Binds
    @Singleton
    abstract fun providesUpdateLocalUserInfoDataSource(
        updateUserInfoDataSourceImpl: UpdateLocalUserInfoDataSourceImpl
    ): UpdateLocalUserInfoDataSource

    @Binds
    @Singleton
    abstract fun providesSearchIngredientDataSource(
        searchIngredientDataSourceImpl : SearchIngredientDataSourceImpl
    ) : SearchIngredientDataSource
}
