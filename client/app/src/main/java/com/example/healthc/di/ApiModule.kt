package com.example.healthc.di

import com.example.healthc.BuildConfig
import com.example.healthc.data.remote.api.SearchFoodService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideOkHttpInterceptor() : OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(Interceptor { chain ->
                val request = chain.request().newBuilder()
                val httpUrl = chain.request().url
                val url = httpUrl.newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.SPOON_API_KEY).build()
                request.url(url)
                chain.proceed(request = request.build())
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
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