package com.example.healthc.di

import com.example.healthc.data.repository.*
import com.example.healthc.data.source.auth.AuthDataSource
import com.example.healthc.data.source.kor_product.KorProductDataSource
import com.example.healthc.data.source.detection.DetectionDataSource
import com.example.healthc.data.source.product.ProductDataSource
import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.data.source.user.UserDataSource
import com.example.healthc.domain.repository.*
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
