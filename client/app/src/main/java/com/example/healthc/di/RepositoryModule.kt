package com.example.healthc.di

import com.example.healthc.data.repository.*
import com.example.healthc.data.source.auth.AuthDataSource
import com.example.healthc.data.source.kor_product.KorProductDataSource
import com.example.healthc.data.source.object_detection.ObjectDetectionDataSource
import com.example.healthc.data.source.product.ProductDataSource
import com.example.healthc.data.source.recipe.RecipeDataSource
import com.example.healthc.data.source.user.local.LocalUserDataSource
import com.example.healthc.data.source.user.UserDataSource
import com.example.healthc.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesAuthRepository(
        authDataSource: AuthDataSource, firebaseAuth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(authDataSource, firebaseAuth)

    @Provides
    fun providesUserRepository(
        userDataSource: UserDataSource, localUserDataSource: LocalUserDataSource
    ) : UserRepository = UserRepositoryImpl(userDataSource, localUserDataSource)

    @Provides
    fun providesProductRepository(
        productDataSource: ProductDataSource, recipeDataSource: RecipeDataSource
    ): ProductRepository = ProductRepositoryImpl(productDataSource, recipeDataSource)

    @Provides
    fun providesKorProductRepository(korProductDataSource: KorProductDataSource)
        : KorProductRepository = KorProductRepositoryImpl(korProductDataSource)

    @Provides
    fun providesObjectDetectionRepository(
        objectDetectionDataSource: ObjectDetectionDataSource, recipeDataSource: RecipeDataSource
    ): ObjectDetectionRepository = ObjectDetectionRepositoryImpl(
        objectDetectionDataSource, recipeDataSource
    )

    @Provides
    fun providesRecipeRepository(
        recipeDataSource: RecipeDataSource
    ): RecipeRepository = RecipeRepositoryImpl(recipeDataSource)
}
