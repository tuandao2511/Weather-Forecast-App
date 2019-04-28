package com.example.sunshineapp_mvvm_koltin.model.remote

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RemoteWeatherDataSource {


    companion object {
        private var INSTANCE: RemoteWeatherDataSource? = null


        fun getInstance() : RemoteWeatherDataSource? {
            if (INSTANCE == null) {
                synchronized(RemoteWeatherDataSource::class.java) {
                    INSTANCE = RemoteWeatherDataSource()
                }
            }
            return INSTANCE
        }

    }

    fun provideRetrofit(): RemoteWeatherService? =
        Retrofit.Builder()
            .baseUrl("https://andfun-weather.udacity.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RemoteWeatherService::class.java)


    fun requestWeatherForecast(query: String, mode: String, units: String, cnt: Int) =
        provideRetrofit()?.requestWeatherForecast(query, mode, units, cnt)



}