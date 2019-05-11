package com.example.weatherforecastapp.ui.detail

import com.example.weatherforecastapp.data.source.AppRepository
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import java.util.*
import javax.inject.Inject

class DetailPresenter @Inject constructor(private val repository: AppRepository,
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