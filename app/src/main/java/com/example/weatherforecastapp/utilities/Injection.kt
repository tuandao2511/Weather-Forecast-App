package com.example.weatherforecastapp.utilities

import android.content.Context
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherDataSource
import com.example.weatherforecastapp.model.SunshineRepository
import com.example.weatherforecastapp.model.database.SunshineDatabase

object Injection {
   fun provideSunshineRepository(context: Context): SunshineRepository {
       val database = SunshineDatabase.getInstace(context)
       return SunshineRepository.getInstance(RemoteWeatherDataSource.getInstance(),
           database?.weatherDao())!!
   }
}