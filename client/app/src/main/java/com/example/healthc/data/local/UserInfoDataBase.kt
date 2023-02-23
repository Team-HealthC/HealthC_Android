package com.example.healthc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthc.data.local.entity.UserInfoEntity

@Database(
    entities = [UserInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class UserInfoDataBase: RoomDatabase() {

    abstract val dao: UserInfoDao
}