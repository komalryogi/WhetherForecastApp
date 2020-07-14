package com.almissbah.weather.ui

import android.os.Bundle
import com.almissbah.weather.R
import com.almissbah.weather.ui.base.WeatherForecastActivity

class MainActivity : WeatherForecastActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
