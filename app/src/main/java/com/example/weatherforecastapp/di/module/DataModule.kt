package com.example.weatherforecastapp.di.module

import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.local.SunshineDatabase
import com.example.weatherforecastapp.data.source.local.SunshineDbOpenHelper
import com.example.weatherforecastapp.data.source.local.SunshineLocalDataSource
import com.example.weatherforecastapp.di.DatabaseInfo
import com.example.weatherforecastapp.di.Local
import com.example.weatherforecastapp.utilities.AppExecutors
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class DataModule {


    @Provides
    @Singleton
    @Local
    fun provideSunshineLocalDataSource(sunshineLocalDataSource: SunshineLocalDataSource)
    : SunshineDataSource {
        return sunshineLocalDataSource
    }


    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName() = "weather.db"


    @Provides
    @Singleton
    fun provideWeatherDatabase(sunshineDbOpenHelper: SunshineDbOpenHelper) : SunshineDatabase {
        return sunshineDbOpenHelper.getDatabase()
    }

    @Provides
    @Singleton
    fun provideExecutor() : AppExecutors {
        return AppExecutors()
    }

}