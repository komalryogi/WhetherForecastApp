package com.almissbah.weather

import com.almissbah.weather.di.component.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class WeatherForecastApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder().application(this).build()
        component.inject(this)
        return component
    }
}