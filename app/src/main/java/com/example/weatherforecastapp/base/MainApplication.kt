package com.example.weatherforecastapp.base

import android.app.Application
import android.util.Log
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.di.component.ApplicationComponent
import com.example.weatherforecastapp.di.component.DaggerApplicationComponent
import com.example.weatherforecastapp.di.module.ApplicationModule
import dagger.android.DaggerApplication
import javax.inject.Inject

class MainApplication : Application() {


    private lateinit var mApplicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
        mApplicationComponent.inject(this)

//        Log.d(".MainApplication", "repository " +repository)

    }

    fun getComponent() = mApplicationComponent
}