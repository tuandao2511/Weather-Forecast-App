package com.example.weatherforecastapp.utilities

import android.content.Context
import com.example.weatherforecastapp.model.SunshineRepository
import com.example.weatherforecastapp.model.database.SunshineDatabase
import com.example.weatherforecastapp.model.remote.RemoteWeatherDataSource

object Injection {
   fun provideSunshineRepository(context: Context): SunshineRepository {
       val database = SunshineDatabase.getInstace(context)
       return SunshineRepository.getInstance(
           RemoteWeatherDataSource.getInstance(),
           database?.weatherDao())!!
   }
}