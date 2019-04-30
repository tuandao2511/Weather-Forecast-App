package com.example.weatherforecastapp.data.source.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather", indices = [Index(value = ["date"],unique = true)])
data class WeatherEntry(
    @PrimaryKey(autoGenerate = true) var id :Int?,
    var weatherIconId : Int,
    var date : Date,
    var min : Double,
    var max : Double,
    var humidity : Double,
    var pressure :  Double,
    var wind : Double,
    var degrees : Double
) {
    constructor() : this(null,0, Date(), 0.0,
        0.0, 0.0, 0.0,0.0,0.0)
}
