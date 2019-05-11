package com.example.weatherforecastapp.ui.detail

import com.example.weatherforecastapp.di.DetailActivityScope
import com.example.weatherforecastapp.di.PerActivity
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class DetailModule(val context: DetailContract.View, val date: Date) {

    @DetailActivityScope
    @Provides
    fun provideView(): DetailContract.View {
        return context
    }

    @Provides
    fun provideDate() :Date {
        return date
    }

    @Provides
    fun providePresenter(presenter: DetailPresenter): DetailContract.Presenter {
        return presenter
    }
}