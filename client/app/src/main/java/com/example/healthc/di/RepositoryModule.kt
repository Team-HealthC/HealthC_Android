package com.example.healthc.di

import com.example.healthc.data.repository.AuthRepositoryImpl
import com.example.healthc.data.repository.FoodRepositoryImpl
import com.example.healthc.data.repository.UserRepositoryImpl
import com.example.healthc.data.source.auth.SignInDataSource
import com.example.healthc.data.source.auth.SignUpDataSource
import com.example.healthc.data.source.food.kor_product.SearchFoodProductSource
import com.example.healthc.data.source.food.ingredient.SearchIngredientDataSource
import com.example.healthc.data.source.food.object_detect.SearchCategoryDataSource
import com.example.healthc.data.source.food.product.*
import com.example.healthc.data.source.local.user.GetLocalUserInfoDataSource
import com.example.healthc.data.source.local.user.UpdateLocalUserInfoDataSource
import com.example.healthc.data.source.user.GetUserInfoDataSource
import com.example.healthc.data.source.user.UpdateUserInfoDataSource
import com.example.healthc.data.source.user.UpdateUserNameDataSource
import com.example.healthc.domain.repository.AuthRepository
import com.example.healthc.domain.repository.FoodRepository
import com.example.healthc.domain.repository.UserRepository
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
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun providesAuthRepository(signInDataSource: SignInDataSource,
                               signUpDataSource: SignUpDataSource,
                               firebaseAuth: FirebaseAuth): AuthRepository
        = AuthRepositoryImpl(signInDataSource, signUpDataSource, firebaseAuth)

    @Provides
    fun providesUserRepository(getUserInfoDataSource: GetUserInfoDataSource,
                               updateLocalUserInfoDataSource: UpdateLocalUserInfoDataSource,
                               updateUserNameDataSource: UpdateUserNameDataSource,
                               getLocalUserInfoDataSource: GetLocalUserInfoDataSource,
                               updateUserInfoDataSource: UpdateUserInfoDataSource
    ) : UserRepository
        = UserRepositoryImpl(getUserInfoDataSource, updateUserInfoDataSource,
        updateUserNameDataSource, updateLocalUserInfoDataSource, getLocalUserInfoDataSource )

    @Provides
    fun providesFoodRepository(searchIngredientDataSource: SearchIngredientDataSource,
                               searchFoodProductSource: SearchFoodProductSource,
                               searchFoodCategoryDataSource: SearchCategoryDataSource,
                               searchProductIdDataSource : SearchProductIdDataSource,
                               searchProductFactsDataSource : SearchProductFactsDataSource,
                               searchProductInfoDataSource: SearchProductInfoDataSource)
        : FoodRepository = FoodRepositoryImpl(searchIngredientDataSource, searchFoodProductSource,
        searchFoodCategoryDataSource, searchProductIdDataSource,
    searchProductInfoDataSource, searchProductFactsDataSource)

}
