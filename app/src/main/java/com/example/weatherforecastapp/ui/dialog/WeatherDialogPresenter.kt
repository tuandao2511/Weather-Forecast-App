package com.example.weatherforecastapp.ui.dialog

import android.util.Log


class WeatherDialogPresenter (private val dialogLocation: WeatherLocationDialogContract.View)
    : WeatherLocationDialogContract.Presenter {

    override fun start() {

    }

    override fun getNewLocationInput(locationInput: String?) {
        if (locationInput!=null){
            Log.d("WeatherDialogPresenter", "locationInput " +locationInput)
            Log.d("WeatherDialogPresenter", "dialogLocation " +dialogLocation)
            dialogLocation.showNewForecasts(locationInput)
        }
    }
}