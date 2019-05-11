package com.example.weatherforecastapp.di.module

import android.app.Application
import android.content.Context
import com.example.weatherforecastapp.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication() : Application {
        return application
    }

    @Provides
    @ApplicationContext
    fun provideContext() : Context{
        return application
    }


}