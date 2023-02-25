package com.example.healthc.data.local

import androidx.room.*
import com.example.healthc.data.local.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Delete
    suspend fun deleteUserInfo(userInfo : UserInfoEntity)

    @Query("select * FROM USER_INFO")
    fun getUserInfo(): Flow<UserInfoEntity>
}