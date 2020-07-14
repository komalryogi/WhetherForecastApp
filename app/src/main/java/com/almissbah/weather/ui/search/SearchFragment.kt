package com.almissbah.weather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.almissbah.weather.R
import com.almissbah.weather.ui.base.WeatherForecastFragment
import com.almissbah.weather.ui.search.adapter.SearchResultAdapter
import com.almissbah.weather.utils.UserInputValidator
import com.almissbah.weather.utils.hide
import com.almissbah.weather.utils.unHide
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


class SearchFragment : WeatherForecastFragment() {

    private var mAdapter: SearchResultAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var searchViewModel: SearchViewModel

    override fun initViewModel() {
        searchViewModel =
            ViewModelProviders.of(
                this,
                viewModelFactory
            )[SearchViewModel::class.java]
    }

    override fun initViews() {
        rvSearchResults.layoutManager = LinearLayoutManager(this.context)
        mAdapter = SearchResultAdapter()
        rvSearchResults.adapter = mAdapter
        btnSearch.setOnClickListener {
            searchViewModel.getCitiesWeather(etSearch.text.toString())

        }

        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.getCitiesWeather(etSearch.text.toString())
            }
            false
        }
        mProgressBar = progressBar
    }

    override fun subscribe() {
        searchViewModel.queryValidator.observe(viewLifecycleOwner, Observer {
            when (it) {
                UserInputValidator.CitiesCount.LessThanThreeCities -> etSearch.error =
                    getString(R.string.less_than_three_error_msg)
                UserInputValidator.CitiesCount.MoreThanSevenCities -> etSearch.error =
                    getString(R.string.more_than_seven_error_msg)
                UserInputValidator.CitiesCount.Valid -> {
                    btnSearch.hide()
                    showLoading()
                    ivNoItems.hide()
                }
            }
        })



        searchViewModel.searchResult.observe(viewLifecycleOwner, Observer {
            btnSearch.unHide()
            hideLoading()
            when (it.action) {
                SearchViewModel.Action.UpdateList -> updateList(it.payload)
                SearchViewModel.Action.ShowNetworkError -> showNetworkError()
            }
        })
    }

    private fun showNetworkError() {
        showSnackbar(view!!, getString(R.string.connection_faild_msg))
        rvSearchResults.hide()
        ivNoItems.unHide()
    }

    private fun updateList(payload: MutableList<CityWeatherWithData>?) {
        rvSearchResults.unHide()
        mAdapter?.setData(payload!!)
        ivNoItems.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        searchViewModel.unSubscribe()
    }

}
