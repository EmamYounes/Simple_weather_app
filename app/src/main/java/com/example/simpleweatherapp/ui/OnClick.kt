package com.example.simpleweatherapp.ui

import com.example.simpleweatherapp.data.db.entities.CityWeatherItem

interface OnClick {
    fun onItemClick(item: CityWeatherItem)
//    fun onIconClick()
}