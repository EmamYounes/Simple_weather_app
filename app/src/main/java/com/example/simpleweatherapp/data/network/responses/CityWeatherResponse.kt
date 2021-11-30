package com.example.simpleweatherapp.data.network.responses

import com.example.simpleweatherapp.data.db.entities.CityWeatherItem

data class CityWeatherResponse(
    val isSuccessful: Boolean,
    val cityWeatherItem: CityWeatherItem
)
