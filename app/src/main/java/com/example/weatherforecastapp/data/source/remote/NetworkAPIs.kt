package com.example.weatherforecastapp.data.source.remote

import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherService

interface NetworkAPIs {
    fun getRemoteWeatherService(): RemoteWeatherService
}