package com.example.healthc.data.local

import androidx.room.*
import com.example.healthc.data.local.entity.UserInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserInfo(userInfo: UserInfoEntity)

    @Query("DELETE FROM USER_INFO WHERE name in (:name)")
    suspend fun deleteUserInfo(name: String)

    @Query("select * from USER_INFO")
    fun getUserInfo(): Flow<UserInfoEntity>
}