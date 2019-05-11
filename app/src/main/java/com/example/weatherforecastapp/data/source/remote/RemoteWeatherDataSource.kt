package com.example.sunshineapp_mvvm_koltin.model.remote

import android.util.Log
import com.example.weatherforecastapp.data.source.SunshineDataSource
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import com.example.weatherforecastapp.data.source.remote.NetworkAPIs
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteWeatherDataSource @Inject constructor(
    private val networkAPIs: NetworkAPIs
) : SunshineDataSource{



    private val format = "json"
    /* The units we want our API to return */
    private val units = "metric"

    private val ctn = 14

    val allCompositeDisposable : MutableList<Disposable> = arrayListOf()

//    companion object {
//        private var INSTANCE: RemoteWeatherDataSource? = null
//
//
//        @JvmStatic fun getInstance(remoteWeatherService: RemoteWeatherService) : RemoteWeatherDataSource {
//            if (INSTANCE == null) {
//                synchronized(RemoteWeatherDataSource::class.java) {
//                    INSTANCE = RemoteWeatherDataSource(remoteWeatherService)
//                }
//            }
//            return INSTANCE!!
//        }
//
//    }

//    fun provideRetrofit(): RemoteWeatherService? =
//        Retrofit.Builder()
//            .baseUrl("https://andfun-weather.udacity.com")
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//            .create(RemoteWeatherService::class.java)


//    fun requestWeatherForecast(query: String, mode: String, units: String, cnt: Int) =
//        provideRetrofit()?.requestWeatherForecast(query, mode, units, cnt)

    override fun getForecasts(locationQuery: String, callback: SunshineDataSource.LoadForecastsCallback) {
        val disposable = networkAPIs.getRemoteWeatherService().requestWeatherForecast(locationQuery,format,units,ctn)?.
            subscribeOn(Schedulers.io())?.
            subscribe({ weatherForecastsStr ->
                Log.d(".Repository ","subcribe " +Thread.currentThread().name)
                val weatherEntries = transform(weatherForecastsStr)
                callback.onForecastsLoaded(weatherEntries)
            },{
                    t -> t?.printStackTrace()
            })
        allCompositeDisposable.add(disposable!!)
    }

    private fun transform(weatherForecastsStr: String): List<WeatherEntry> {
        return WeatherJsonParser.parse(weatherForecastsStr)
    }

    override fun getForecasts(callback: SunshineDataSource.LoadForecastsCallback) {
    }

    override fun deleteAllForecasts() {
    }

    override fun insertForecasts(forecasts: List<WeatherEntry>) {
    }

    override fun getForecast(date: Date, callback: SunshineDataSource.getForecastCallback) {

    }

}