package com.almissbah.weather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.almissbah.weather.data.Resource
import com.almissbah.weather.data.remote.model.CityWeather
import com.almissbah.weather.data.repo.CityWeatherRepo
import com.almissbah.weather.utils.UserInputValidator
import io.reactivex.disposables.Disposable
import retrofit2.Response
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val weatherRepo: CityWeatherRepo) : ViewModel() {
    companion object {
        const val TAG = "SearchViewModel"
    }

    enum class Action { UpdateList, ShowNetworkError }

    private var disposable: Disposable? = null
    private val _queryValidator = MutableLiveData<UserInputValidator.CitiesCount>()
    val queryValidator: LiveData<UserInputValidator.CitiesCount> = _queryValidator

    private val _searchResult =
        MutableLiveData<Resource<MutableList<CityWeatherWithData>, Action>>()
    val searchResult: LiveData<Resource<MutableList<CityWeatherWithData>, Action>> = _searchResult


    private fun fetchDataFromRepo(list: List<String>) {
        disposable = weatherRepo.getAllCitiesWeather(list).subscribe { it ->
            if (it.isEmpty()) {
                handleError()
            } else {
                handleSuccess(it, list)
            }
        }
    }

    private fun handleError() {
        _searchResult.postValue(Resource(null, Action.ShowNetworkError, ""))
    }

    private fun handleSuccess(
        it: MutableList<Response<CityWeather>>,
        list: List<String>
    ) {
        val resultsList = mutableListOf<CityWeatherWithData>()
        it.forEach {
            if (it.isSuccessful) {
                resultsList.add(
                    CityWeatherWithData(
                        it.body()!!.cityName!!,
                        it.body(),
                        CityWeatherWithData.Result.Found
                    )
                )
            } else {
                resultsList.add(
                    CityWeatherWithData(
                        "null",
                        null,
                        CityWeatherWithData.Result.NotFound
                    )
                )
            }
        }
        recoverCitiesNames(resultsList, list)
        _searchResult.postValue(Resource(resultsList, Action.UpdateList, ""))
    }

    private fun recoverCitiesNames(
        resultsList: MutableList<CityWeatherWithData>,
        list: List<String>
    ) {
        resultsList.forEachIndexed { index, searchResult ->
            if (searchResult.result == CityWeatherWithData.Result.NotFound) {
                searchResult.cityName = list[index]
            }
        }
    }

    fun getCitiesWeather(query: String) {
        val list = UserInputValidator.getValidCityList(query)
        val result = UserInputValidator.validateCitiesCount(list.size)
        if (result == UserInputValidator.CitiesCount.Valid) {
            fetchDataFromRepo(list)
        }
        _queryValidator.postValue(result)
    }

    fun unSubscribe() {
        disposable?.dispose()
        disposable = null
    }
}