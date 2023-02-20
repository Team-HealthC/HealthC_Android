package com.example.healthc.di

import android.app.Application
import androidx.room.Room
import com.example.healthc.data.local.Converters
import com.example.healthc.data.local.UserInfoDao
import com.example.healthc.data.local.UserInfoDataBase
import com.example.healthc.data.utils.GsonParser
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserInfoDatabase(app: Application): UserInfoDataBase {
        return Room.databaseBuilder(
            app, UserInfoDataBase::class.java, "user_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()

    }

    @Provides
    @Singleton
    fun providesUserInfoDao(dataBase: UserInfoDataBase) : UserInfoDao
        = dataBase.dao
}