package com.almissbah.weather.ui.forecast.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.almissbah.weather.data.remote.model.CityWeatherWithDate


class ForecastDiffUtilsCallback(
    private val oldList: List<CityWeatherWithDate>,
    private val newList: List<CityWeatherWithDate>
) :
    DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].cityName == newList[newItemPosition].cityName
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].windInfo?.speed == newList[newItemPosition].windInfo?.speed
    }


    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition]
    }

}