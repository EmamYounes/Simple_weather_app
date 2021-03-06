package com.example.simpleweatherapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityWeatherItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var cityName: String? = "",
    var weatherInfo: String? = "",
    var weatherImage: String? = "",
    var description: String? = "",
    var temp: String? = "",
    var humidity: String? = "",
    var windSpeed: String? = "",
    var date: String? = "",
)
