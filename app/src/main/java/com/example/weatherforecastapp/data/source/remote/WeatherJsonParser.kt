package com.example.sunshineapp_mvvm_koltin.model.remote

import com.example.sunshineapp_mvvm_koltin.utilities.SunshineDateUtils
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class WeatherJsonParser {

    companion object {

        // Weather information. Each day's forecast info is an element of the "list" array
        private val OWM_LIST = "list"

        private val OWM_PRESSURE = "pressure"
        private val OWM_HUMIDITY = "humidity"
        private val OWM_WINDSPEED = "speed"
        private val OWM_WIND_DIRECTION = "deg"

        // All temperatures are children of the "temp" object
        private val OWM_TEMPERATURE = "temp"

        // Max temperature for the day
        private val OWM_MAX = "max"
        private val OWM_MIN = "min"

        private val OWM_WEATHER = "weather"
        private val OWM_WEATHER_ID = "id"

        private val OWM_MESSAGE_CODE = "cod"

        fun fromJson(forecastJson: JSONObject): List<WeatherEntry> {

            val jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST)
            val normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday()

            val weatherEntries = ArrayList<WeatherEntry>()

            for (i in 0 until jsonWeatherArray.length()) {
                val dayForecaseJsonObj = jsonWeatherArray.getJSONObject(i)

                val dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i
                val weatherEntry = fromJson(dayForecaseJsonObj, dateTimeMillis)

                weatherEntries.add(weatherEntry)
            }
            return weatherEntries
        }

        @Throws(JSONException::class)
        fun fromJson(dayForecast: JSONObject, dateTimeMillis: Long): WeatherEntry {
            // We ignore all the datetime values embedded in the JSON and assume that
            // the values are returned in-order by day (which is not guaranteed to be correct).

            val pressure = dayForecast.getDouble(OWM_PRESSURE)
            val humidity = dayForecast.getDouble(OWM_HUMIDITY)
            val windSpeed = dayForecast.getDouble(OWM_WINDSPEED)
            val windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION)


            // Description is in a child array called "weather", which is 1 element long.
            // That element also contains a weather code.
            val weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0)

            val weatherId = weatherObject.getInt(OWM_WEATHER_ID)


            //  Temperatures are sent by Open Weather Map in a child object called "temp".
            val temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE)
            val max = temperatureObject.getDouble(OWM_MAX)
            val min = temperatureObject.getDouble(OWM_MIN)

            val weatherEntry = WeatherEntry()
            weatherEntry.weatherIconId = weatherId
            weatherEntry.pressure = pressure
            weatherEntry.humidity = humidity
            weatherEntry.wind = windSpeed
            weatherEntry.degrees = windDirection
            weatherEntry.max = max
            weatherEntry.min = min
            weatherEntry.date = Date(dateTimeMillis)
            // Create the weather entry object
            return weatherEntry

        }


        fun parse(forecastJsonStr: String?): List<WeatherEntry> {
            var forecastJsonObj = JSONObject(forecastJsonStr)

            val weatherEntryList = fromJson(forecastJsonObj)

            return weatherEntryList

        }
    }
}
