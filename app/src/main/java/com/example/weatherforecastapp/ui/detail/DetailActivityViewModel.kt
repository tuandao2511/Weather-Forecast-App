package com.example.weatherforecastapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.model.SunshineRepository
import com.example.weatherforecastapp.model.database.WeatherEntry
import java.util.*

class DetailActivityViewModel(val repository: SunshineRepository?, val date: Date): ViewModel() {

    private var mWeather : LiveData<WeatherEntry>?

    init {
        mWeather = repository?.getWeatherByDate(date)
    }


    fun getWeather(): LiveData<WeatherEntry>? {
        return mWeather
    }
}