package com.almissbah.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.almissbah.weather.R
import com.almissbah.weather.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvForecast.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_forecast)
        }


        tvSearch.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_search)
        }
    }
}
