package com.example.healthc.di

import com.example.healthc.BuildConfig
import com.example.healthc.data.remote.api.SearchFoodService
import com.example.healthc.data.remote.api.SearchFoodProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @SpoonRetrofit
    @Provides
    @Singleton
    fun provideSpoonRetrofit(
        client : OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SPOON_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    @DataGoRetrofit
    @Provides
    @Singleton
    fun provideDataGoRetrofit(
        client : OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DATA_GO_PRODUCT_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun searchFoodService(
        @SpoonRetrofit retrofit: Retrofit
    ): SearchFoodService = retrofit.create(SearchFoodService::class.java)

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