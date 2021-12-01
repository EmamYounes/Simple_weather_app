package com.example.simpleweatherapp.ui

import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.databinding.WeatherItemBinding
import com.xwray.groupie.databinding.BindableItem

class WeatherViewItem(
    private val weatherItem: CityWeatherItem,
    private val onClick: OnClick,
) : BindableItem<WeatherItemBinding>() {


    override fun getLayout() = R.layout.weather_item

    override fun bind(viewBinding: WeatherItemBinding, position: Int) {
        viewBinding.weatherItem = weatherItem
        viewBinding.handler = onClick
    }
}