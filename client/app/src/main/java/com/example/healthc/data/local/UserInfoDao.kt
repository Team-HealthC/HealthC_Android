package com.example.healthc.data.local

import androidx.room.*
import com.example.healthc.data.entity.local.LocalUserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: LocalUserInfoEntity)

    @Query("DELETE FROM USER_INFO WHERE name in (:name)")
    suspend fun deleteUserInfo(name: String)

    @Query("select * from USER_INFO")
    fun getUserInfo(): Flow<LocalUserInfoEntity>
}