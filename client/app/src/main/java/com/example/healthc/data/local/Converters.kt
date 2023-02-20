package com.example.healthc.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.healthc.data.utils.GsonParser
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {
    @TypeConverter
    fun fromUserInfoToJson(userInfo: List<String>) : String {
        return jsonParser.toJson(
            userInfo,
            object : TypeToken<List<String>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromJsonToUserInfo(json : String): List<String> {
        return jsonParser.fromJson<List<String>>(
            json,
            object : TypeToken<List<String>>(){}.type
        ) ?: emptyList()
    }
}