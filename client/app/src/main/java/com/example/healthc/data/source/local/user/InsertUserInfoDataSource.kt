package com.example.healthc.data.source.local.user

interface InsertUserInfoDataSource {
    suspend fun insertUserInfo(uid: String)
}