package com.example.weatherforecastapp.data.source.remote

import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIHelper @Inject constructor(private val remoteWeatherService: RemoteWeatherService)
    : NetworkAPIs{

    override fun getRemoteWeatherService() = remoteWeatherService

}