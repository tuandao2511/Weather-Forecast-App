package com.example.weatherforecastapp.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherforecastapp.R

@SuppressLint("ValidFragment")
class WeatherLocationDialog(context: Context): DialogFragment(),
    WeatherLocationDialogContract.View {

    override lateinit var presenter: WeatherLocationDialogContract.Presenter

    private lateinit var mEditText: EditText
    private lateinit var mView: View

    private lateinit var callback: OnLocationInputListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        initView()

        return showDialog()
    }

    override fun showNewForecasts(location: String) {
        callback.onLocationInput(location)
    }

    fun showDialog(): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.location_weather_forecast)
        builder.setView(mView)
            .setPositiveButton(R.string.postionButton) { dialog, which ->
                val locationStr = mEditText.text?.toString()
                presenter.getNewLocationInput(locationStr)
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
        this.callback = onLocationInputListener
    }


    interface OnLocationInputListener {
        fun onLocationInput(location: String)
    }
}