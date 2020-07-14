package com.almissbah.weather.ui.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almissbah.weather.data.Resource
import com.almissbah.weather.data.remote.CallbackWrapper
import com.almissbah.weather.data.remote.model.City5DaysForecast
import com.almissbah.weather.data.remote.model.CityForecastRequest
import com.almissbah.weather.data.repo.CityForecastRepo
import com.almissbah.weather.utils.location.LocationData
import com.almissbah.weather.utils.location.LocationLiveData
import javax.inject.Inject

class ForecastViewModel @Inject constructor(
    private val forecastRepo: CityForecastRepo,
    private val locationLiveData: LocationLiveData
) :
    ViewModel() {
    var mLocationData: LocationData? = null
    fun getLocationData() = locationLiveData

    enum class Action { Success, NotFound, ShowNetworkError }

    private val _cityForecast = MutableLiveData<Resource<City5DaysForecast, Action>>()
    val cityForecast: LiveData<Resource<City5DaysForecast, Action>> = _cityForecast

    fun getCityForecast(locationData: LocationData?) {
        if (locationData == null) return
        mLocationData = locationData
        forecastRepo.getCityForecast(CityForecastRequest(locationData.lat, locationData.lon))
            .subscribe(CallbackWrapper(object : CallbackWrapper.HttpCallback<City5DaysForecast> {
                override fun onSuccess(t: City5DaysForecast?) {
                    _cityForecast.postValue(Resource(t, Action.Success, ""))
                }

                override fun onNetworkError() {
                    _cityForecast.postValue(Resource(null, Action.ShowNetworkError, ""))
                }

                override fun onServerError() {
                    _cityForecast.postValue(Resource(null, Action.ShowNetworkError, ""))
                }

                override fun onNotFound() {
                    _cityForecast.postValue(Resource(null, Action.NotFound, ""))
                }
            }))
    }

    fun reTry() {
        getCityForecast(mLocationData!!)
    }

}