package com.almissbah.weather.ui.search.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.almissbah.weather.ui.search.CityWeatherWithData


class SearchDiffUtilsCallback(
    private val oldList: List<CityWeatherWithData>,
    private val newList: List<CityWeatherWithData>
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
        return oldList[oldItemPosition].cityWeather?.windInfo?.speed == newList[newItemPosition].cityWeather?.windInfo?.speed
    }


    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition]
    }

}