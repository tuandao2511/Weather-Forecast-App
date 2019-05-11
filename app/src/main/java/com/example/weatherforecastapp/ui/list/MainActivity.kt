package com.example.weatherforecastapp.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.base.MainApplication
import com.example.weatherforecastapp.data.source.local.WeatherEntry
import com.example.weatherforecastapp.di.component.DaggerMainActivityComponent
import com.example.weatherforecastapp.di.component.MainActivityComponent
import com.example.weatherforecastapp.ui.detail.DetailActivity
import com.example.weatherforecastapp.ui.dialog.WeatherDialogPresenter
import com.example.weatherforecastapp.ui.dialog.WeatherLocationDialog
import dagger.android.DaggerApplication_MembersInjector
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View, WeatherLocationDialog.OnLocationInputListener{

    private val TAG = MainActivity::class.java.simpleName

    @Inject
    override lateinit var presenter: MainContract.Presenter

    @Inject
    lateinit var mWeatherLocationDialog: WeatherLocationDialog


    private lateinit var mAdapter: ForecastAdapter

    private lateinit var recyclerViewForecast: RecyclerView
    private lateinit var pbLoadingIndicator: ProgressBar
    private lateinit var mMainActivityComponent: MainActivityComponent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        mMainActivityComponent = DaggerMainActivityComponent.builder()
            .applicationComponent((application as MainApplication).getComponent())
            .mainModule(MainModule(this))
            .build()

        mMainActivityComponent.inject(this)
        mWeatherLocationDialog.setLocationInputListener(this)
        presenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.location_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId){
            R.id.location_input -> {

//                val weatherLocationDialog = WeatherLocationDialog(this)
//                weatherLocationDialog.setLocationInputListener(this)
//                val dialogPresenter = WeatherDialogPresenter(weatherLocationDialog)
//                weatherLocationDialog.show(supportFragmentManager,TAG)
                mWeatherLocationDialog.show(supportFragmentManager,TAG)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }


    private fun initPresenter() {
//        presenter = MainPresenter(Injection.provideSunshineRepository(applicationContext),
//            this)
//        presenter.start()


    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        recyclerViewForecast = findViewById(R.id.recycleWeatherForecasts)
        pbLoadingIndicator = findViewById(R.id.pbLoadingIndicator)
        mAdapter = ForecastAdapter(this,ArrayList<WeatherEntry>(),{date :Date -> onItemClick(date)})
        val layoutmanager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        recyclerViewForecast.layoutManager = layoutmanager
        recyclerViewForecast.setHasFixedSize(true)
        recyclerViewForecast.adapter = mAdapter
    }

    override fun showListForecasts(forecasts: List<WeatherEntry>) {
        mAdapter.swapForecasts(forecasts)
        recyclerViewForecast.scrollToPosition(0)
    }

    fun onItemClick(date: Date) {
        val weatherDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        val timestamp = date.time
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp)
        startActivity(weatherDetailIntent)
    }

    override fun showWeatherDataView() {
        pbLoadingIndicator.visibility = View.INVISIBLE
        recyclerViewForecast.visibility = View.VISIBLE
    }

    override fun showLoading(){
        pbLoadingIndicator.visibility = View.VISIBLE
        recyclerViewForecast.visibility = View.INVISIBLE
    }

    override fun onLocationInput(location: String) {
        Log.d(TAG,"location $location")
        presenter.loadNewWeatherForecasts(location)
    }

}
