package com.example.weatherforecastapp.ui.list

import android.util.Log
import com.example.weatherforecastapp.data.source.AppRepository
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import com.example.weatherforecastapp.di.MainActivityScope
import javax.inject.Inject

const val LOCATION_QUERY = "Mountain View, CA"

class MainPresenter @Inject constructor(val repository: AppRepository,
                    @MainActivityScope val mainView: MainContract.View)
    : MainContract.Presenter{


    override fun start() {
        loadWeatherForecasts(LOCATION_QUERY)
    }

    override fun loadNewWeatherForecasts(locationQuery: String) {
        loadWeatherForecasts(locationQuery)
    }

    fun loadWeatherForecasts(locationQuery: String) {
        repository.getForecasts(locationQuery,object : SunshineDataSource.LoadForecastsCallback{
            override fun onForecastsLoaded(forecasts: List<WeatherEntry>) {
                Log.d(".Callback"," loadWeatherForecasts")

                repository.getForecasts(object : SunshineDataSource.LoadForecastsCallback{
                    override fun onForecastsLoaded(forecasts: List<WeatherEntry>) {
                        processForecasts(forecasts)
                    }

                    override fun onDataNotAvaiable() {
                    }

                })
            }

            override fun onDataNotAvaiable() {
            }

        })
    }

    private fun processForecasts(forecasts: List<WeatherEntry>) {
        if (forecasts.isEmpty()) {
            mainView.showLoading()
        }else {
            mainView.showListForecasts(forecasts)
            mainView.showWeatherDataView()
        }
    }
}