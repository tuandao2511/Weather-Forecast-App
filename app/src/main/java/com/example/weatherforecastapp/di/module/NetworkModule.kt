package com.example.weatherforecastapp.di.module

import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherDataSource
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherService
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.remote.APIHelper
import com.example.weatherforecastapp.data.source.remote.NetworkAPIs
import com.example.weatherforecastapp.di.NetworkAPI
import com.example.weatherforecastapp.di.Remote
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://andfun-weather.udacity.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(retrofit: Retrofit) :RemoteWeatherService
            = retrofit.create(RemoteWeatherService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitHelper(apiHelper: APIHelper) :NetworkAPIs {
        return apiHelper
    }

    @Provides
    @Singleton
    @Remote
    fun provideRemoteDataSource(remoteWeatherDataSource: RemoteWeatherDataSource): SunshineDataSource {
        return remoteWeatherDataSource
    }
}