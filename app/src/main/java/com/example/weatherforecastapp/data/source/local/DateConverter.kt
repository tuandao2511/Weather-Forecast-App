package com.example.weatherforecastapp.data.source.local

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?) : Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?) :Long? {
        return date?.time?.toLong()
    }
}
