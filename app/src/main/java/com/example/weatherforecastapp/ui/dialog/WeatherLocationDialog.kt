package com.example.weatherforecastapp.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.base.MainApplication
import com.example.weatherforecastapp.di.component.DaggerMainActivityComponent
import com.example.weatherforecastapp.di.component.MainActivityComponent
import com.example.weatherforecastapp.ui.list.MainActivity
import com.example.weatherforecastapp.ui.list.MainModule
import javax.inject.Inject

@SuppressLint("ValidFragment")
class WeatherLocationDialog(val context: MainActivity): DialogFragment(),
    WeatherLocationDialogContract.View {

    @Inject
    override lateinit var presenter: WeatherLocationDialogContract.Presenter

    private lateinit var mMainActivityComponent: MainActivityComponent


    private lateinit var mEditText: EditText
    private lateinit var mView: View

    private  var callback: OnLocationInputListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        initView()

        mMainActivityComponent = DaggerMainActivityComponent.builder()
            .applicationComponent((context.application as MainApplication).getComponent())
            .mainModule(MainModule(context))
            .build()
        mMainActivityComponent.inject(this)
        Log.d("WeatherLocationDialog","presenter " +presenter)
        Log.d("WeatherLocationDialog", "Callback $callback")

        return showDialog()
    }

    override fun showNewForecasts(location: String) {
        Log.d("WeatherLocationDialog", "Callback:" +callback)
        callback?.onLocationInput(location)
    }

    fun showDialog(): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.location_weather_forecast)
        builder.setView(mView)
            .setPositiveButton(R.string.postionButton) { dialog, which ->
                val locationStr = mEditText.text?.toString()
                Log.d("WeatherLocationDialog", "Callback $callback")
//                presenter.getNewLocationInput(locationStr) //cai nay co the sai nhe vi khi fragment mat di, callback =null
                if (locationStr != null) {
                    callback?.onLocationInput(locationStr)
                }

            }
            .setNegativeButton(R.string.nagativeButton) { dialog, which ->
                getDialog().cancel()
            }

        return builder.create()

    }



    private fun initView() {
        mView = requireActivity().layoutInflater.inflate(R.layout.dialog_location_input,null)
        mEditText = mView.findViewById<EditText>(R.id.location_input)
    }

    fun setLocationInputListener(onLocationInputListener :OnLocationInputListener) {
        Log.d("WeatherLocationDialog", "onLocationInputListener $onLocationInputListener")
        callback = onLocationInputListener
    }


    interface OnLocationInputListener {
        fun onLocationInput(location: String)
    }
}