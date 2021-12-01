package com.example.simpleweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
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

    fun initCityWeatherItem() {
        repository.initCityWeatherItem()
    }

    fun initSelectedItem() {
        repository.initSelectedItem()
    }


    fun getSelectedItemData() : CityWeatherItem? {
        return repository.getSelectedItemData()
    }
    fun getWeatherListData() : List<CityWeatherItem>? {
        return repository.getWeatherListData()
    }

    fun setSelectedItemData(item: CityWeatherItem) {
        repository.setSelectedItemData(item)
    }

}
