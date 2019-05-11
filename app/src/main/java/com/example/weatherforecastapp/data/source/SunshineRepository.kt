package com.example.weatherforecastapp.data.source

import android.util.Log
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherDataSource
import com.example.weatherforecastapp.data.source.local.SunshineLocalDataSource
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import com.example.weatherforecastapp.di.Local
import com.example.weatherforecastapp.di.Remote
import com.example.weatherforecastapp.di.Repository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunshineRepository @Inject constructor(@Remote val remoteWeatherDataSource: SunshineDataSource,
                                             @Local val localDataSource: SunshineDataSource
) : AppRepository {

//    companion object {
//        private var INSTANCE: SunshineRepository? = null
//
//        @JvmStatic fun getInstance(remoteWeatherDataSource: RemoteWeatherDataSource,
//                                   localDataSource: SunshineLocalDataSource) : SunshineRepository {
//            if (INSTANCE == null) {
//                synchronized(SunshineRepository::class.java) {
//                    INSTANCE =
//                        SunshineRepository(
//                            remoteWeatherDataSource,
//                            localDataSource
//                        )
//                }
//            }
//            return INSTANCE!!
//        }
//    }

//    fun getWeatherForecasts() : LiveData<List<WeatherEntry>>?{
//        return weatherDao?.getWeatherForecasts()
//    }
//
//    fun getWeatherByDate(date: Date): LiveData<WeatherEntry>? {
//        return weatherDao?.getWeatherByDate(date)
//    }


    override fun getForecasts(locationQuery: String, callback: SunshineDataSource.LoadForecastsCallback) {
        remoteWeatherDataSource.getForecasts(locationQuery,object : SunshineDataSource.LoadForecastsCallback {
            override fun onForecastsLoaded(forecasts: List<WeatherEntry>) {
                Log.d(".Callback"," getForecasts")
                deleteAllForecasts()
                localDataSource.insertForecasts(forecasts)
                callback.onForecastsLoaded(forecasts)
            }

            override fun onDataNotAvaiable() {
                callback.onDataNotAvaiable()
            }

        })
    }

    override fun getForecasts(callback: SunshineDataSource.LoadForecastsCallback) {
        localDataSource.getForecasts(object : SunshineDataSource.LoadForecastsCallback{
            override fun onForecastsLoaded(forecasts: List<WeatherEntry>) {
                Log.d(".Callback"," getForecasts From database")
                callback.onForecastsLoaded(forecasts)
            }

            override fun onDataNotAvaiable() {
            }

        })
    }

    override fun deleteAllForecasts() {
        localDataSource.deleteAllForecasts()
    }

    override fun getForecast(date: Date, callback: SunshineDataSource.getForecastCallback) {
        localDataSource.getForecast(date, object : SunshineDataSource.getForecastCallback{
            override fun onForecastLoaded(forecast: WeatherEntry) {
                callback.onForecastLoaded(forecast)
            }

            override fun onDataNotAvaiable() {
            }

        })
    }

    override fun insertForecasts(forecasts: List<WeatherEntry>) {
    }

}
