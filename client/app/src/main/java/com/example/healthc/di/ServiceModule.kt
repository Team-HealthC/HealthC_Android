package com.example.healthc.di

import com.example.healthc.BuildConfig
import com.example.healthc.data.service.ProductService
import com.example.healthc.data.service.KorProductService
import com.example.healthc.data.service.ObjectDetectionService
import com.example.healthc.data.service.RecipeService
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
object ServiceModule {

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
    fun providesProductService(
        @SpoonRetrofit retrofit: Retrofit
    ): ProductService = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun providesObjectDetectionService(
        @SpoonRetrofit retrofit: Retrofit
    ): ObjectDetectionService = retrofit.create(ObjectDetectionService::class.java)

    @Provides
    @Singleton
    fun providesRecipeService(
        @SpoonRetrofit retrofit: Retrofit
    ): RecipeService = retrofit.create(RecipeService::class.java)

    @Provides
    @Singleton
    fun providesKorProductService(
        @DataGoRetrofit retrofit: Retrofit
    ) : KorProductService = retrofit.create(KorProductService::class.java)

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DataGoRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SpoonRetrofit