package com.example.weatherforecastapp.data.source.local

import android.util.Log
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.utilities.AppExecutors
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SunshineLocalDataSource @Inject constructor(
    val appDatabase: SunshineDatabase,
    val executor: AppExecutors
) : SunshineDataSource {



    override fun getForecasts(callback: SunshineDataSource.LoadForecastsCallback) {
//        executor.diskIO.execute { callback.onForecastsLoaded(weatherDao.getWeatherForecasts()) }
        executor.diskIO.execute {
            val forecasts = appDatabase.weatherDao().getWeatherForecasts()
            Log.d(".LocalDataSource ", " forecasts: $forecasts")
            executor.mainThread.execute {
                callback.onForecastsLoaded(forecasts)
            }
        }
    }


    override fun getForecasts(locationQuery: String, callback: SunshineDataSource.LoadForecastsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAllForecasts() {
        executor.diskIO.execute {
            Log.d(".Callback"," deleteAllForecasts")
            appDatabase.weatherDao().deleteAllData()
        }
    }

    override fun insertForecasts(forecasts: List<WeatherEntry>) {
        executor.diskIO.execute {
            Log.d(".Callback"," insertForecasts")
            appDatabase.weatherDao().bulkInsert(forecasts)
        }
    }

    override fun getForecast(date: Date, callback: SunshineDataSource.getForecastCallback) {
        executor.diskIO.execute {
            val forecast = appDatabase.weatherDao().getWeatherByDate(date)
            executor.mainThread.execute {
                if (forecast != null) {
                    callback.onForecastLoaded(forecast)
                } else {
                    callback.onDataNotAvaiable()
                }
            }
        }
    }


//    companion object {
//        private var INSTANCE: SunshineLocalDataSource? = null
//
//        @JvmStatic fun getInstance(
//            weatherDao: WeatherDao,
//            appExecutors: AppExecutors
//        ): SunshineLocalDataSource {
//            if (INSTANCE == null) {
//                synchronized(SunshineLocalDataSource::class.java) {
//                    INSTANCE = SunshineLocalDataSource(weatherDao,appExecutors)
//                }
//            }
//            return INSTANCE!!
//        }
//    }
}
