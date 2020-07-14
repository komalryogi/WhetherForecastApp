package com.almissbah.weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class CityWeather(
    @SerializedName("name") val cityName: String?,
    @SerializedName("weather") val weatherDescription: List<WeatherDescription>?,
    @SerializedName("main") val mainInfo: MainInfo?,
    @SerializedName("wind") val windInfo: WindInfo?,
    @SerializedName("dt") val timeStamp: Long?
)

