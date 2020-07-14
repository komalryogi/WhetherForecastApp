package com.almissbah.weather.data.remote.model

data class City5DaysForecast(
    val list: MutableList<CityWeatherWithDate>,
    val city: City
)