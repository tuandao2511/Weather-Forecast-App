package com.example.weatherforecastapp.data.source.local

import android.content.Context
import androidx.room.Room
import com.example.weatherforecastapp.di.ApplicationContext
import com.example.weatherforecastapp.di.DatabaseInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunshineDbOpenHelper @Inject constructor(@ApplicationContext val context: Context,
                                               @DatabaseInfo val name: String) {
    private lateinit var mDatabase: SunshineDatabase

    init {
        mDatabase = Room.databaseBuilder(context, SunshineDatabase::class.java, name)
            .build()
    }

    fun getDatabase() = mDatabase
}