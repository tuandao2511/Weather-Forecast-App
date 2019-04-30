package com.example.weatherforecastapp.ui.dialog

import android.app.Dialog
import com.example.weatherforecastapp.base.BasePresenter
import com.example.weatherforecastapp.base.BaseView

interface WeatherLocationDialogContract {

    interface View : BaseView<Presenter>{
        fun showNewForecasts(locationInput: String)
    }

    interface Presenter : BasePresenter{
        fun getNewLocationInput(locationInput: String?)
    }
}