package com.almissbah.weather.di.component.module

import com.almissbah.weather.data.remote.WeatherApiService
import com.almissbah.weather.data.repo.AppRepository
import com.almissbah.weather.data.repo.CityForecastRepo
import com.almissbah.weather.data.repo.CityWeatherRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepoModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideAppRepository(
        weatherApiService: WeatherApiService
    ): AppRepository {
        return AppRepository(
            weatherApiService
        )
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideWeatherRepository(
        repo: AppRepository
    ): CityWeatherRepo {
        return repo
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideForecastRepository(
        repo: AppRepository
    ): CityForecastRepo {
        return repo
    }

}