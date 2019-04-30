package com.example.sunshineapp_mvvm_koltin.model.remote

import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteWeatherService {

    @GET("/weather")
    fun requestWeatherForecast(@Query("q") query: String,
                               @Query("mode") mode: String,
                               @Query("units") units: String,
                               @Query("cnt") cnt: Int) : Observable<String>
}