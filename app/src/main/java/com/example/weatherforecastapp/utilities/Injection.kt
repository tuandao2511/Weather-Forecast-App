package com.example.weatherforecastapp.utilities

import android.content.Context
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherDataSource
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherService
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.data.source.local.SunshineDatabase
import com.example.weatherforecastapp.data.source.local.SunshineLocalDataSource
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Injection {
   fun provideSunshineRepository(context: Context): SunshineRepository {
       val database = SunshineDatabase.getInstace(context)
       return SunshineRepository.getInstance(RemoteWeatherDataSource.getInstance(Injection.provideRetrofit()),
           SunshineLocalDataSource.getInstance(database.weatherDao(),AppExecutors()))
   }


    fun provideRetrofit(): RemoteWeatherService =
        Retrofit.Builder()
            .baseUrl("https://andfun-weather.udacity.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RemoteWeatherService::class.java)
}