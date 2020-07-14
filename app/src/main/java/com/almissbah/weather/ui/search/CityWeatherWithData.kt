package com.almissbah.weather.ui.search

import com.almissbah.weather.data.remote.model.CityWeather

data class CityWeatherWithData(
    var cityName: String,
    val cityWeather: CityWeather?,
    val result: Result
) {
    enum class Result { Found, NotFound }
}