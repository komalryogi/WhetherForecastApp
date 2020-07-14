package com.almissbah.weather.data.repo

import com.almissbah.weather.data.remote.model.City5DaysForecast
import com.almissbah.weather.data.remote.model.CityForecastRequest
import io.reactivex.Observable
import retrofit2.Response

interface CityForecastRepo {
    fun getCityForecast(request: CityForecastRequest): Observable<Response<City5DaysForecast>>
}