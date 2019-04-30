package com.example.weatherforecastapp.ui.list

import com.example.weatherforecastapp.base.BasePresenter
import com.example.weatherforecastapp.base.BaseView
import com.example.weatherforecastapp.data.source.local.WeatherEntry

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showListForecasts(forecasts: List<WeatherEntry>)
        fun showWeatherDataView()
        fun showLoading()
    }

    interface Presenter : BasePresenter {
        fun loadNewWeatherForecasts(locationQuery: String)
    }
}