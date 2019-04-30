package com.example.weatherforecastapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapp.model.SunshineRepository
import java.util.*

class DetailViewModelFactory(val repository: SunshineRepository?, val date: Date): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivityViewModel(repository,date) as T
    }
}