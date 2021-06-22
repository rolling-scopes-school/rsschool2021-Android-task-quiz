package com.rsschool.quiz.room

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.ArrayList

class Converters {

    @TypeConverter
    fun fromList(value: ArrayList<String>?): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toList(value: String): ArrayList<String>? {
        return Json.decodeFromString(value)
    }
}