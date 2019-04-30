package com.example.weatherforecastapp.data.source

import com.example.weatherforecastapp.data.source.local.WeatherEntry
import java.util.*

interface SunshineDataSource {

    interface LoadForecastsCallback{

        fun onForecastsLoaded(forecasts: List<WeatherEntry>)

        fun onDataNotAvaiable()
    }

    interface getForecastCallback{

        fun onForecastLoaded(forecast: WeatherEntry)

        fun onDataNotAvaiable()
    }



    fun getForecasts(locationQuery: String, callback: LoadForecastsCallback)

    fun getForecasts(callback: LoadForecastsCallback)

    fun getForecast(date: Date, callback: getForecastCallback)

    fun deleteAllForecasts()

    fun insertForecasts(forecasts: List<WeatherEntry>)


}