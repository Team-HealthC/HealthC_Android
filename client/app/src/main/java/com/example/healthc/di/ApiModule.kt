package com.example.healthc.di

import com.example.healthc.BuildConfig
import com.example.healthc.data.remote.api.SearchFoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SPOON_BASE_URI)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun searchFoodService(
        retrofit: Retrofit
    ): SearchFoodService = retrofit.create(SearchFoodService::class.java)

}