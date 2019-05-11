package com.example.weatherforecastapp.di.component

import com.example.weatherforecastapp.base.MainApplication
import com.example.weatherforecastapp.data.source.AppRepository
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.di.Repository
import com.example.weatherforecastapp.di.module.ApplicationModule
import com.example.weatherforecastapp.di.module.DataModule
import com.example.weatherforecastapp.di.module.NetworkModule
import com.example.weatherforecastapp.di.module.RepositoryModule
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface ApplicationComponent {
    fun inject(main: MainApplication)

    fun getRepository(): AppRepository
}