package com.almissbah.weather.data.remote

import com.almissbah.weather.data.remote.model.City5DaysForecast
import com.almissbah.weather.data.remote.model.CityWeather
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather")
    fun getCityInfo(@Query("q") cityName: String): Observable<Response<CityWeather>>

    @GET("/data/2.5/forecast")
    fun fetchForecastByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Observable<Response<City5DaysForecast>>

}