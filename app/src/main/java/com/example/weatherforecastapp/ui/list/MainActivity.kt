package com.example.weatherforecastapp.ui.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.model.SunshineRepository
import com.example.weatherforecastapp.model.base.BaseView
import com.example.weatherforecastapp.utilities.Injection

class MainActivity : AppCompatActivity(), MainContract.View{
    override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}
