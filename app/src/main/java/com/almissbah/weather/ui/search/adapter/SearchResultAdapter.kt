package com.almissbah.weather.ui.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.almissbah.weather.R
import com.almissbah.weather.ui.search.CityWeatherWithData
import com.almissbah.weather.utils.gone
import com.almissbah.weather.utils.unHide


class SearchResultAdapter :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private var mList: MutableList<CityWeatherWithData> = mutableListOf()

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
            val searchResult = payloads[0] as CityWeatherWithData
            updateItemView(holder, position, searchResult)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result: CityWeatherWithData = mList[holder.adapterPosition]
        holder.bindResult(result)
    }


    fun setData(newList: MutableList<CityWeatherWithData>) {

        val diffCallback =
            SearchDiffUtilsCallback(
                mList,
                newList
            )
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList = newList
        diffResult.dispatchUpdatesTo(this)


    }

    private fun updateItemView(holder: ViewHolder, position: Int, result: CityWeatherWithData) {
        mList[position] = result
        holder.bindResult(result)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCityName: TextView = view.findViewById(R.id.tvTitle)
        var tvTemp: TextView = view.findViewById(R.id.tvTemp)
        var tvWindSpeed: TextView = view.findViewById(R.id.tvWindSpeed)
        var tvWeatherDescription: TextView = view.findViewById(R.id.tvWeatherDescription)
        var tvCityNotFound: TextView = view.findViewById(R.id.tvCityNotFound)
        var layoutCityData: ConstraintLayout = view.findViewById(R.id.layoutCityData)
        fun bindResult(result: CityWeatherWithData) {
            if (result.result == CityWeatherWithData.Result.Found) {
                tvCityNotFound.gone()
                layoutCityData.unHide()
                tvCityName.text = result.cityName
                val mainInfo = result.cityWeather?.mainInfo
                tvTemp.text =
                    "${mainInfo?.minTemp} - ${mainInfo?.maxTemp} K"
                tvWindSpeed.text =
                    "${result.cityWeather?.windInfo?.speed} m/s"
                var weatherString = ""
                result.cityWeather?.weatherDescription?.forEach { weatherString += "${it.description} \n" }
                tvWeatherDescription.text = weatherString
            } else {
                tvCityName.text = result.cityName
                layoutCityData.gone()
                tvCityNotFound.unHide()
            }

        }
    }

}