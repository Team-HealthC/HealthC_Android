package com.healthc.data.di

import com.healthc.data.source.auth.AuthDataSource
import com.healthc.data.source.auth.AuthDataSourceImpl
import com.healthc.data.source.kor_product.KorProductDataSource
import com.healthc.data.source.kor_product.KorProductDataSourceImpl
import com.healthc.data.source.detection.DetectionDataSource
import com.healthc.data.source.detection.DetectionDataSourceImpl
import com.healthc.data.source.recipe.RecipeDataSource
import com.healthc.data.source.recipe.RecipeDataSourceImpl
import com.healthc.data.source.product.ProductDataSource
import com.healthc.data.source.product.ProductDataSourceImpl
import com.healthc.data.source.user.*
import com.healthc.data.source.user.UserDataSource
import com.healthc.data.source.user.UserDataSourceImpl
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
    abstract fun provideAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun providesUserDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun providesKorProductDataSource(
        korProductDataSourceImpl : KorProductDataSourceImpl
    ) : KorProductDataSource

    @Binds
    @Singleton
    abstract fun providesDetectionDataSource(
        detectionDataSourceImpl: DetectionDataSourceImpl
    ) : DetectionDataSource

    @Binds
    @Singleton
    abstract fun providesSearchProductInfoDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ) : ProductDataSource

    @Binds
    @Singleton
    abstract fun providesRecipeDataSource(
        recipeDataSourceImpl: RecipeDataSourceImpl
    ): RecipeDataSource
}
