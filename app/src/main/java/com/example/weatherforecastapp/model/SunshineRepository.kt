package com.example.weatherforecastapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.sunshineapp_mvvm_koltin.model.remote.RemoteWeatherDataSource
import com.example.sunshineapp_mvvm_koltin.model.remote.WeatherJsonParser
import com.example.weatherforecastapp.model.database.WeatherDao
import com.example.weatherforecastapp.model.database.WeatherEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class SunshineRepository(val remoteWeatherDataSource: RemoteWeatherDataSource?, val weatherDao: WeatherDao?)  {


//    private val locationQuery = "Mountain View, CA"

    /* The format we want our API to return */
    private val format = "json"
    /* The units we want our API to return */
    private val units = "metric"

    private val ctn = 14

    val allCompositeDisposable : MutableList<Disposable> = arrayListOf()

    /* The query parameter allows us to provide a location string to the API */

    companion object {
        private var INSTANCE: SunshineRepository? = null

        fun getInstance(remoteWeatherDataSource: RemoteWeatherDataSource?,weatherDao: WeatherDao?) : SunshineRepository? {
            if (INSTANCE == null) {
                synchronized(SunshineRepository::class.java) {
                    INSTANCE = SunshineRepository(remoteWeatherDataSource,weatherDao)
                }
            }
            return INSTANCE
        }
    }

    fun getWeatherForecasts() : LiveData<List<WeatherEntry>>?{
        return weatherDao?.getWeatherForecasts()
    }

    fun getWeatherByDate(date: Date): LiveData<WeatherEntry>? {
        return weatherDao?.getWeatherByDate(date)
    }

    fun requestWeatherForecasts(locationQuery: String) {
        val disposable = remoteWeatherDataSource?.requestWeatherForecast(locationQuery,format,units,ctn)?.
            subscribeOn(Schedulers.io())?.
            subscribe({ weatherForecastsStr ->
                weatherDao?.deleteAllData()
                Log.d(".Repository ","subcribe " +Thread.currentThread().name)
                val weatherEntries = transform(weatherForecastsStr)
                weatherDao?.bulkInsert(weatherEntries)
            },{
                t -> t?.printStackTrace()
            })
        allCompositeDisposable.add(disposable!!)

    }

    fun transform(weatherForecastsStr: String): List<WeatherEntry> {
        return WeatherJsonParser.parse(weatherForecastsStr)
    }








}