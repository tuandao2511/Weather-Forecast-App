package com.example.weatherforecastapp.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather ")
    fun getWeatherForecasts() : LiveData<List<WeatherEntry>>

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date : Date) : LiveData<WeatherEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(weatherEntries: List<WeatherEntry>)


    @Query("DELETE FROM weather")
    fun deleteAllData()
}