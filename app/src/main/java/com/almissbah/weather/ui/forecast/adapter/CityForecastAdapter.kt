package com.almissbah.weather.ui.forecast.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.almissbah.weather.R
import com.almissbah.weather.data.remote.model.CityWeatherWithDate
import com.almissbah.weather.utils.gone


class CityForecastAdapter :
    RecyclerView.Adapter<CityForecastAdapter.ViewHolder>() {

    private var mList: MutableList<CityWeatherWithDate> = mutableListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val context: Context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)

        val listItem: View = layoutInflater.inflate(R.layout.weather_list_item, viewGroup, false)
        return ViewHolder(listItem)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val searchResult = payloads[0] as CityWeatherWithDate
            updateItemView(holder, position, searchResult)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result: CityWeatherWithDate = mList[holder.adapterPosition]
        holder.bindResult(result)
    }


    fun setData(newList: MutableList<CityWeatherWithDate>) {

        val diffCallback =
            ForecastDiffUtilsCallback(
                mList,
                newList
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList = newList
        diffResult.dispatchUpdatesTo(this)


    }

    private fun updateItemView(holder: ViewHolder, position: Int, result: CityWeatherWithDate) {
        mList[position] = result

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        var tvTemp: TextView = view.findViewById(R.id.tvTemp)
        var tvWindSpeed: TextView = view.findViewById(R.id.tvWindSpeed)
        var tvWeatherDescription: TextView = view.findViewById(R.id.tvWeatherDescription)
        var tvCityNotFound: TextView = view.findViewById(R.id.tvCityNotFound)
        fun bindResult(result: CityWeatherWithDate) {
            tvCityNotFound.gone()
            tvTitle.text = result.date
            val mainInfo = result.mainInfo
            tvTemp.text =
                "${mainInfo?.minTemp} - ${mainInfo?.maxTemp} K"
            tvWindSpeed.text =
                "${result.windInfo?.speed} m/s"
            var weatherString = ""
            result.weatherDescription?.forEach { weatherString += "${it.description} \n" }
            tvWeatherDescription.text = weatherString


        }
    }

}