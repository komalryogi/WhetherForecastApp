package com.almissbah.weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class MainInfo(
    @SerializedName("temp_min") val minTemp: Double,
    @SerializedName("temp_max") val maxTemp: Double
)