package com.example.weatherforecastapp.ui.detail

import com.example.weatherforecastapp.base.BasePresenter
import com.example.weatherforecastapp.base.BaseView
import com.example.weatherforecastapp.data.source.local.WeatherEntry

interface DetailContract {
    interface View : BaseView<Presenter>{
        fun showTask(forecast: WeatherEntry)
    }

    interface Presenter : BasePresenter{

    }
}