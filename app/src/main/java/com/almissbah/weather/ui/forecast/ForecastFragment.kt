package com.almissbah.weather.ui.forecast

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.almissbah.weather.R
import com.almissbah.weather.ui.base.WeatherForecastFragment
import com.almissbah.weather.ui.forecast.adapter.CityForecastAdapter
import com.almissbah.weather.utils.hide
import com.almissbah.weather.utils.location.LocationUtils.Companion.checkPermissions
import com.almissbah.weather.utils.location.LocationUtils.Companion.isLocationEnabled
import com.almissbah.weather.utils.unHide
import kotlinx.android.synthetic.main.fragment_forcast.*
import javax.inject.Inject

class ForecastFragment : WeatherForecastFragment() {
    companion object {
        private const val PERMISSION_ID = 101
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var forecastViewModel: ForecastViewModel

    private var mAdapter: CityForecastAdapter? = null

    override fun initViewModel() {
        forecastViewModel =
            ViewModelProviders.of(
                this,
                viewModelFactory
            )[ForecastViewModel::class.java]

    }

    override fun initViews() {
        rvForecast.layoutManager = LinearLayoutManager(this.context)
        mAdapter = CityForecastAdapter()
        rvForecast.adapter = mAdapter

        mProgressBar = progressBar
    }

    override fun subscribe() {
        subscribeToLocationUpdates()
        forecastViewModel.cityForecast.observe(viewLifecycleOwner, Observer {
            hideLoading()
            when (it.action) {
                ForecastViewModel.Action.Success -> {
                    tvCurrentCity.text = "${it.payload?.city!!.name}, ${it.payload?.city!!.country}"
                    mAdapter?.setData(it!!.payload!!.list)
                }
                ForecastViewModel.Action.NotFound -> showNetworkError()
                ForecastViewModel.Action.ShowNetworkError -> showNetworkError()
            }
        })
    }

    private fun showNetworkError() {
        errorLayout.unHide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forcast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvRetry.setOnClickListener {
            forecastViewModel.reTry()
            showLoading()
            errorLayout.hide()
        }
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                subscribeToLocationUpdates()
            }
        }
    }

    private fun subscribeToLocationUpdates() {
        if (checkPermissions(context!!)) {
            forecastViewModel.getLocationData().observe(viewLifecycleOwner, Observer {
                forecastViewModel.getCityForecast(it)
            })

            if (!isLocationEnabled(context!!)) {
                launchSettingsScreen()
            }
        } else {
            requestPermissions()
        }
    }

    private fun launchSettingsScreen() {
        showToast(getString(R.string.turn_on_gps_msg))
        val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }


}
