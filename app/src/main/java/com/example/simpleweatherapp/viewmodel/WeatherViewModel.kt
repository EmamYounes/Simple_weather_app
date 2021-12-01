package com.example.simpleweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.simpleweatherapp.data.repositories.WeatherRepository
import com.example.simpleweatherapp.util.lazyDeferred

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherList by lazyDeferred {
        repository.getWeatherList()
    }


    val cityWeatherItem by lazyDeferred {
        repository.getWeatherItem()
    }

    val errorException by lazyDeferred {
        repository.getErrorException()
    }

    suspend fun addNewCity(cityName: String) {
        repository.addNewCity(cityName)

    }

}
