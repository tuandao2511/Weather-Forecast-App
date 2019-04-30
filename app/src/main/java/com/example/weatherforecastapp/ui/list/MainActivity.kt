package com.example.weatherforecastapp.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.ui.list.MainActivityViewModel

import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialog
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.model.SunshineRepository
import com.example.weatherforecastapp.model.database.SunshineDatabase
import com.example.weatherforecastapp.model.database.WeatherEntry
import com.example.weatherforecastapp.model.remote.RemoteWeatherDataSource
import com.example.weatherforecastapp.ui.detail.DetailActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.forecast_list_item.*
import java.util.*

class MainActivity : AppCompatActivity() {




    private val TAG = MainActivity::class.java.simpleName
    private lateinit var mMainActivityViewModel: MainActivityViewModel
    private lateinit var mForecastAdapter: ForecastAdapter



    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutmanager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recycleWeatherForecasts.layoutManager = layoutmanager
        recycleWeatherForecasts.setHasFixedSize(true)

        mForecastAdapter = ForecastAdapter(this,ArrayList<WeatherEntry>(),{ date :Date -> onItemClick(date)})
        recycleWeatherForecasts.adapter = mForecastAdapter

        val remoteWeatherDataSource = RemoteWeatherDataSource.getInstance()
        val weatherDao = SunshineDatabase.getInstace(this)?.weatherDao()
        val repository = SunshineRepository.getInstance(remoteWeatherDataSource,weatherDao)
        val factory = MainViewModelFactory(repository)

        mMainActivityViewModel = ViewModelProviders.of(this,factory).get(MainActivityViewModel::class.java)
        mMainActivityViewModel.getListWeatherEntry()!!.observe(this, Observer { weatherEntries ->
            mForecastAdapter.swapForecasts(weatherEntries as List<WeatherEntry>)
            recycleWeatherForecasts.scrollToPosition(0)

            if (weatherEntries.size!=0) showWeatherDataView()
            else showLoading()
        })
    }


    fun showWeatherDataView() {
        pbLoadingIndicator.visibility = View.INVISIBLE
        recycleWeatherForecasts.visibility = View.VISIBLE
    }

    fun showLoading(){
        pbLoadingIndicator.visibility = View.VISIBLE
        recycleWeatherForecasts.visibility = View.INVISIBLE
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.location_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.location_input -> {
                val weatherLocationDialog = WeatherLocationDialog(this)
                weatherLocationDialog.show(supportFragmentManager,TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    fun onItemClick(date: Date) {
        val weatherDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        val timestamp = date.time
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp)
        startActivity(weatherDetailIntent)
    }
}
