package com.example.weatherforecastapp.ui.list

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.utilities.DiffUtilCallback
import com.example.weatherforecastapp.utilities.SunshineDateUtils
import com.example.weatherforecastapp.utilities.SunshineWeatherUtils
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.model.database.WeatherEntry
import kotlinx.android.synthetic.main.forecast_list_item.view.*
import java.util.*

class ForecastAdapter(val context: Context, var items: List<WeatherEntry>, val listener: (Date)->Unit ): RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>() {

    private val VIEW_TYPE_TODAY = 0
    private val VIEW_TYPE_FUTURE_DAY = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapterViewHolder {

        val layoutId = getLayoutIdByType(viewType)
        val view = LayoutInflater.from(context).inflate(layoutId, parent,false )
        return ForecastAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (items == null) return 0
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return VIEW_TYPE_TODAY
        return VIEW_TYPE_FUTURE_DAY
    }

    private fun getLayoutIdByType(viewType: Int): Int {
        when (viewType) {

            VIEW_TYPE_TODAY -> {
                return R.layout.list_item_forecast_today
            }

            VIEW_TYPE_FUTURE_DAY -> {
                return R.layout.forecast_list_item
            }

            else -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }
    }

    fun getImageResourceId(weatherIconId: Int, position: Int): Int {
        val viewType = getItemViewType(position)
        when (viewType) {
            VIEW_TYPE_TODAY -> return SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherIconId)
            VIEW_TYPE_FUTURE_DAY -> return SunshineWeatherUtils
                .getSmallArtResourceIdForWeatherCondition(weatherIconId)
            else -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param forecastAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ForecastAdapterViewHolder, position: Int) {
        val currentWeather = items.get(position)

        /****************
         * Weather Icon *
         ****************/
        val weatherIconId = currentWeather.weatherIconId
        val weatherImageResourceId = getImageResourceId(weatherIconId, position)
        holder.iconView.setImageResource(weatherImageResourceId)

        /****************
         * Weather Date *
         ****************/
        val dateMillis = currentWeather.date.time
        val dateStr = SunshineDateUtils.getFriendlyDateString(context, dateMillis,false)
        holder.dateView.text = dateStr


        /***********************
         * Weather Description *
         ***********************/
        val description = SunshineWeatherUtils.getStringForWeatherCondition(context, weatherIconId)
        /* Create the accessibility (a11y) String from the weather description */
        val descriptionA11y = context.getString(R.string.a11y_forecast, description)

        /* Set the text and content description (for accessibility purposes) */
        holder.descriptionView.text = description
        holder.descriptionView.contentDescription = descriptionA11y

        /**************************
         * High (max) temperature *
         **************************/

        val highInCelsius = currentWeather.max
        /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either °C or °F to the temperature
          * String.
          */
        val highString = SunshineWeatherUtils.formatTemperature(context, highInCelsius)
        /* Create the accessibility (a11y) String from the weather description */
        val highA11y = context.getString(R.string.a11y_high_temp, highString)

        /* Set the text and content description (for accessibility purposes) */
        holder.highTempView.text = highString
        holder.highTempView.contentDescription = highA11y

        /*************************
         * Low (min) temperature *
         *************************/
        val lowInCelsius = currentWeather.min
        /*
          * If the user's preference for weather is fahrenheit, formatTemperature will convert
          * the temperature. This method will also append either °C or °F to the temperature
          * String.
          */
        val lowString = SunshineWeatherUtils.formatTemperature(context, lowInCelsius)
        val lowA11y = context.getString(R.string.a11y_low_temp, lowString)

        /* Set the text and content description (for accessibility purposes) */
        holder.lowTempView.text = lowString
        holder.lowTempView.contentDescription = lowA11y

        holder.bind(currentWeather.date,listener)

    }


    fun swapForecasts(newForecast: List<WeatherEntry>) {
        if (items == null) {
            items = newForecast
            notifyDataSetChanged()
        }else {
            var diffCallback = DiffUtilCallback(items, newForecast)
            var result = DiffUtil.calculateDiff(diffCallback)
            items = newForecast
            result.dispatchUpdatesTo(this)
        }
    }


    class ForecastAdapterViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val iconView = view.weather_icon
        val dateView = view.date
        val descriptionView = view.weather_description
        val highTempView = view.high_temperature
        val lowTempView = view.low_temperature
        val mView = view

        fun bind(date: Date, listener: (Date) -> Unit) {
            mView.setOnClickListener { listener(date) }
        }

    }

    interface ForecastAdapterOnItemClickHandler {
        fun onItemClick(date: Date)
    }

}