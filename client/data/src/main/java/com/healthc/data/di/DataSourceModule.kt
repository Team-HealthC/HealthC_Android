package com.healthc.data.di

import com.healthc.data.source.auth.AuthDataSource
import com.healthc.data.source.auth.AuthDataSourceImpl
import com.healthc.data.source.kor_product.KorProductDataSource
import com.healthc.data.source.kor_product.KorProductDataSourceImpl
import com.healthc.data.source.detection.DetectionDataSource
import com.healthc.data.source.detection.DetectionDataSourceImpl
import com.healthc.data.source.detection.LocalDetectionDataSource
import com.healthc.data.source.detection.LocalDetectionDataSourceImpl
import com.healthc.data.source.ingredient.IngredientDataSource
import com.healthc.data.source.ingredient.IngredientDataSourceImpl
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
    abstract fun bindsAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(
        userDataSourceImpl: UserDataSourceImpl
    ): UserDataSource

    @Binds
    @Singleton
    abstract fun bindsKorProductDataSource(
        korProductDataSourceImpl : KorProductDataSourceImpl
    ) : KorProductDataSource

    @Binds
    @Singleton
    abstract fun bindsDetectionDataSource(
        detectionDataSourceImpl: DetectionDataSourceImpl
    ) : DetectionDataSource

    @Binds
    @Singleton
    abstract fun bindsSearchProductInfoDataSource(
        productDataSourceImpl: ProductDataSourceImpl
    ) : ProductDataSource

    @Binds
    @Singleton
    abstract fun bindsRecipeDataSource(
        recipeDataSourceImpl: RecipeDataSourceImpl
    ): RecipeDataSource

    @Binds
    @Singleton
    abstract fun bindsLocalDetectionDataSource(
        localDetectionDataSourceImpl: LocalDetectionDataSourceImpl
    ): LocalDetectionDataSource

    @Binds
    @Singleton
    abstract fun bindsIngredientDataSource(
        ingredientDataSourceImpl: IngredientDataSourceImpl
    ): IngredientDataSource
}
