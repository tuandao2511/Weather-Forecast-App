package com.example.weatherforecastapp.ui.dialog

class WeatherDialogPresenter(val dialogLocation: WeatherLocationDialogContract.View)
    : WeatherLocationDialogContract.Presenter {

    init {
        dialogLocation.presenter = this
    }

    override fun start() {

    }

    override fun getNewLocationInput(locationInput: String?) {
        if (locationInput!=null){
            dialogLocation.showNewForecasts(locationInput)
        }
    }
}