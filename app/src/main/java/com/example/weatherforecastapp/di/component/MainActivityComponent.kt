package com.example.weatherforecastapp.di.component

import com.example.weatherforecastapp.di.MainActivityScope
import com.example.weatherforecastapp.di.PerActivity
import com.example.weatherforecastapp.di.module.ApplicationModule
import com.example.weatherforecastapp.ui.detail.DetailActivity
import com.example.weatherforecastapp.ui.detail.DetailModule
import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialog
import com.example.weatherforecastapp.ui.list.MainActivity
import com.example.weatherforecastapp.ui.list.MainModule
import dagger.Component

@MainActivityScope
@Component(modules = [MainModule::class], dependencies = [ApplicationComponent::class])
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(weatherLocationDialog: WeatherLocationDialog)

}