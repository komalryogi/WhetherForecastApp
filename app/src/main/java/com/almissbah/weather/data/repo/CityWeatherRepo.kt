package com.almissbah.weather.data.repo

import com.almissbah.weather.data.remote.model.CityWeather
import com.almissbah.weather.data.remote.model.CityWeatherRequest
import io.reactivex.Observable
import retrofit2.Response

interface CityWeatherRepo {
    fun getCityWeather(request: CityWeatherRequest): Observable<Response<CityWeather>>
    fun getAllCitiesWeather(list: List<String>): Observable<MutableList<Response<CityWeather>>>
}