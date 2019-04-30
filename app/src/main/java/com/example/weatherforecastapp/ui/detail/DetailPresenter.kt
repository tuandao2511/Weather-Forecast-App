package com.example.weatherforecastapp.ui.detail

import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import java.util.*

class DetailPresenter(private val repository: SunshineRepository,
                      private val detailView: DetailContract.View,
                      private val date: Date
) : DetailContract.Presenter{

    override fun start() {
        openForecast()
    }

    private fun openForecast() {
        repository.getForecast(date, object : SunshineDataSource.getForecastCallback {
            override fun onForecastLoaded(forecast: WeatherEntry) {
                showTask(forecast)
            }

            override fun onDataNotAvaiable() {
            }

        })
    }

    private fun showTask(forecast: WeatherEntry) {
        if (forecast != null) detailView.showTask(forecast)
    }
}