package com.example.simpleweatherapp.ui

import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.databinding.HistoricalWeatherItemBinding
import com.xwray.groupie.databinding.BindableItem

class HistoricalWeatherViewItem(
    private val weatherItem: CityWeatherItem,
    private val onClick: OnClick,
) : BindableItem<HistoricalWeatherItemBinding>() {


    override fun getLayout() = R.layout.historical_weather_item

    override fun bind(viewBinding: HistoricalWeatherItemBinding, position: Int) {
        viewBinding.weatherItem = weatherItem
        viewBinding.handler = onClick
    }
}