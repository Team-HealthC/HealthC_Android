package com.healthc.data.di

import com.healthc.data.repository.*
import com.healthc.data.source.auth.AuthDataSource
import com.healthc.data.source.kor_product.KorProductDataSource
import com.healthc.data.source.detection.DetectionDataSource
import com.healthc.data.source.product.ProductDataSource
import com.healthc.data.source.recipe.RecipeDataSource
import com.healthc.data.source.user.UserDataSource
import com.healthc.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    fun providesAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository = AuthRepositoryImpl(authDataSource)

    @Provides
    fun providesUserRepository(
        userDataSource: UserDataSource
    ) : UserRepository = UserRepositoryImpl(userDataSource)

    @Provides
    fun providesProductRepository(
        productDataSource: ProductDataSource
    ): ProductRepository = ProductRepositoryImpl(productDataSource)

    @Provides
    fun providesKorProductRepository(korProductDataSource: KorProductDataSource)
        : KorProductRepository = KorProductRepositoryImpl(korProductDataSource)

    @Provides
    fun providesDetectionRepository(
        detectionDataSource: DetectionDataSource
    ): DetectionRepository = DetectionRepositoryImpl(detectionDataSource)

    @Provides
    fun providesRecipeRepository(
        recipeDataSource: RecipeDataSource
    ): RecipeRepository = RecipeRepositoryImpl(recipeDataSource)
}
