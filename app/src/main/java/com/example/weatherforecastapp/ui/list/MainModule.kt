package com.example.weatherforecastapp.ui.list

import android.content.Context
import androidx.fragment.app.DialogFragment
import com.example.weatherforecastapp.di.MainActivityScope
import com.example.weatherforecastapp.di.PerActivity
import com.example.weatherforecastapp.ui.dialog.WeatherDialogPresenter
import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialog
import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialogContract
import dagger.Module
import dagger.Provides

@Module
class MainModule(val context: MainContract.View) {

    @MainActivityScope
    @Provides
    fun provideView(): MainContract.View {
        return context
    }

    @Provides
    fun provideMainPresenter(presenter: MainPresenter) :MainContract.Presenter{
        return presenter
    }

    @MainActivityScope
    @Provides
    fun provideWeatherDialogView(): WeatherLocationDialog {
        return WeatherLocationDialog(context as MainActivity)
    }

    @Provides
    fun provideWeatherDialogPresenter(weatherLocationDialog: WeatherLocationDialog)
            : WeatherLocationDialogContract.Presenter {
            return WeatherDialogPresenter(weatherLocationDialog)
    }
}