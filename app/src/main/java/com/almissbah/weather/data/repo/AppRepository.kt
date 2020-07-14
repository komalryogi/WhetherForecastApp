package com.almissbah.weather.data.repo

import com.almissbah.weather.data.remote.WeatherApiService
import com.almissbah.weather.data.remote.model.City5DaysForecast
import com.almissbah.weather.data.remote.model.CityForecastRequest
import com.almissbah.weather.data.remote.model.CityWeather
import com.almissbah.weather.data.remote.model.CityWeatherRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*

class AppRepository(private val weatherApiService: WeatherApiService) : CityForecastRepo,
    CityWeatherRepo {

    override fun getCityForecast(request: CityForecastRequest): Observable<Response<City5DaysForecast>> {
        return weatherApiService.fetchForecastByCoordinates(request.lat, request.lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCityWeather(request: CityWeatherRequest): Observable<Response<CityWeather>> {
        return weatherApiService.getCityInfo(request.cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAllCitiesWeather(list: List<String>): Observable<MutableList<Response<CityWeather>>> {
        val observables = buildObservableList(list)
        return Observable.zip(observables) { args ->
            mapArgsToResponseList(args)
        }.onErrorReturn {
            return@onErrorReturn mutableListOf()
        }
    }

    private fun mapArgsToResponseList(args: Array<Any>): MutableList<Response<CityWeather>> {
        val list: MutableList<Response<CityWeather>> =
            ArrayList()
        for (i in args) {
            list.add(i as Response<CityWeather>)
        }
        return list
    }

    private fun buildObservableList(list: List<String>): MutableList<Observable<Response<CityWeather>>> {
        val observables = mutableListOf<Observable<Response<CityWeather>>>()
        list.forEach {
            val request = CityWeatherRequest(it)
            observables.add(getCityWeather(request))
        }
        return observables
    }
}