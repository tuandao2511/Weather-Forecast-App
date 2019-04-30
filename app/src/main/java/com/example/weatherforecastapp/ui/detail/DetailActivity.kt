package com.example.weatherforecastapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.sunshineapp_mvvm_koltin.utilities.SunshineDateUtils
import com.example.sunshineapp_mvvm_koltin.utilities.SunshineWeatherUtils
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import com.example.weatherforecastapp.databinding.ActivityDetailBinding
import com.example.weatherforecastapp.utilities.Injection
import java.util.*

class DetailActivity : AppCompatActivity(), DetailContract.View{

    override lateinit var presenter: DetailContract.Presenter
    private lateinit var mDetailBinding: ActivityDetailBinding

    companion object {
        val WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail)
        supportActionBar?.hide()

        val timestamp = intent.getLongExtra(WEATHER_ID_EXTRA,-1)
        val date = Date(timestamp)

        initPresenter(date)
    }

    private fun initPresenter(date: Date) {
        presenter = DetailPresenter(Injection.provideSunshineRepository(applicationContext),
            this, date)
        presenter.start()
    }

    override fun showTask(weatherEntry: WeatherEntry) {
        /****************
         * Weather Icon *
         ****************/

        val weatherId = weatherEntry.weatherIconId
        val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId)

        mDetailBinding.primaryInfo.weatherIcon.setImageResource(weatherImageId)

        /****************
         * Weather Date *
         ****************/

        val localDateMidnightGmt = weatherEntry.date.time
        val dateText = SunshineDateUtils.getFriendlyDateString(this@DetailActivity, localDateMidnightGmt, true)
        mDetailBinding.primaryInfo.date.text = dateText

        /***********************
         * Weather Description *
         ***********************/

        val description = SunshineWeatherUtils.getStringForWeatherCondition(this@DetailActivity, weatherId)
        val descriptionA11y = getString(R.string.a11y_forecast, description)
        mDetailBinding.primaryInfo.weatherDescription.text = description
        mDetailBinding.primaryInfo.weatherDescription.contentDescription = descriptionA11y
        mDetailBinding.primaryInfo.weatherDescription.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         **************************/

        val maxInCelsius = weatherEntry.max
        val highString = SunshineWeatherUtils.formatTemperature(this@DetailActivity, maxInCelsius)
        val highA11y = getString(R.string.a11y_high_temp, highString)

        mDetailBinding.primaryInfo.highTemperature.text = highString
        mDetailBinding.primaryInfo.highTemperature.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         *************************/

        val minInCelsius = weatherEntry.min
        val lowString = SunshineWeatherUtils.formatTemperature(this@DetailActivity, minInCelsius)
        val lowA11y = getString(R.string.a11y_low_temp, lowString)

        mDetailBinding.primaryInfo.lowTemperature.text = lowString
        mDetailBinding.primaryInfo.lowTemperature.contentDescription = lowA11y

        /************
         * Humidity *
         ************/

        val humidityString = getString(R.string.format_humidity, weatherEntry?.humidity)
        val humidityA11y = getString(R.string.a11y_humidity, humidityString)

        mDetailBinding.extraDetails.humidity.text = humidityString
        mDetailBinding.extraDetails.humidity.contentDescription = humidityA11y

        mDetailBinding.extraDetails.humidity.contentDescription = humidityA11y

        /****************************
         * Wind speed and direction *
         ****************************/

        val windSpeed = weatherEntry.wind
        val windDirection = weatherEntry.degrees
        val windString = SunshineWeatherUtils.getFormattedWind(this@DetailActivity, windSpeed, windDirection)
        val windA11y = getString(R.string.a11y_wind, windString)

        mDetailBinding.extraDetails.windMeasurement.text = windString
        mDetailBinding.extraDetails.windMeasurement.contentDescription = windA11y
        mDetailBinding.extraDetails.windLabel.contentDescription = windA11y

        /************
         * Pressure *
         ************/

        val pressureString = getString(R.string.format_pressure, weatherEntry.pressure)
        val pressureA11y = getString(R.string.a11y_pressure, pressureString)

        mDetailBinding.extraDetails.pressure.text = pressureString
        mDetailBinding.extraDetails.pressure.contentDescription = pressureA11y
        mDetailBinding.extraDetails.pressureLabel.contentDescription = pressureA11y

    }


}
