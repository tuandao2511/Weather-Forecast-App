package com.example.weatherforecastapp.utilities

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherforecastapp.model.database.WeatherEntry

class DiffUtilCallback(val oldList: List<WeatherEntry>, val newList: List<WeatherEntry>): DiffUtil.Callback() {



    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date.equals(newList[newItemPosition].date)
    }
}