package com.example.healthc.di

import com.example.healthc.BuildConfig
import com.example.healthc.data.remote.api.SearchFoodIngredientService
import com.example.healthc.data.remote.api.SearchFoodProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @SpoonRetrofit
    @Provides
    @Singleton
    fun provideSpoonRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SPOON_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @DataGoRetrofit
    @Provides
    @Singleton
    fun provideDataGoRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DATA_GO_PRODUCT_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun searchFoodIngredientService(
        @SpoonRetrofit retrofit: Retrofit
    ): SearchFoodIngredientService = retrofit.create(SearchFoodIngredientService::class.java)

    @Provides
    @Singleton
    fun searchFoodProductService(
        @DataGoRetrofit retrofit: Retrofit
    ) : SearchFoodProductService = retrofit.create(SearchFoodProductService::class.java)

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DataGoRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SpoonRetrofit