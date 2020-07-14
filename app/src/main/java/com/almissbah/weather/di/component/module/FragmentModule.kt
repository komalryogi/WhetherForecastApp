package com.almugdad.takhlesy.di.module

import com.almissbah.weather.ui.forecast.ForecastFragment
import com.almissbah.weather.ui.home.HomeFragment
import com.almissbah.weather.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeForecastFragment(): ForecastFragment
}