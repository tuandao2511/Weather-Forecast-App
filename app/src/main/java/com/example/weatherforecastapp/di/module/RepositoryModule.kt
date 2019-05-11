package com.example.weatherforecastapp.di.module

import com.example.weatherforecastapp.data.source.AppRepository
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.SunshineRepository
import com.example.weatherforecastapp.di.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataModule::class, NetworkModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(sunshineRepository: SunshineRepository) :AppRepository{
        return sunshineRepository
    }

}