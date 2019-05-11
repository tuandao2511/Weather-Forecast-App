package com.example.weatherforecastapp.di.component

import com.example.weatherforecastapp.di.DetailActivityScope
import com.example.weatherforecastapp.ui.detail.DetailActivity
import com.example.weatherforecastapp.ui.detail.DetailModule
import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialog
import dagger.Component

@DetailActivityScope
@Component(modules = [DetailModule::class], dependencies = [ApplicationComponent::class])
interface DetailActivityComponent {
    fun inject(detailActivity: DetailActivity)
}