package com.example.simpleweatherapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleweatherapp.data.db.AppDatabase
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.data.network.MyApi
import com.example.simpleweatherapp.data.network.SafeApiRequest
import com.example.simpleweatherapp.data.preferences.PreferenceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val MINIMUM_INTERVAL = 6

class WeatherRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val weatherList = MutableLiveData<List<CityWeatherItem>>()
    private val cityWeatherItem = MutableLiveData<CityWeatherItem>()

    fun getWeatherList(): LiveData<List<CityWeatherItem>> {
        return weatherList
    }

    fun getWeatherItem(): LiveData<CityWeatherItem> {
        return cityWeatherItem
    }

    suspend fun addNewCity(cityName: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    apiRequest { api.getCityWeather(cityName, "f5cb0b965ea1564c50c6f1b74534d823") }
                val list = mutableListOf<CityWeatherItem>()
                if (weatherList.value?.isNotEmpty() == true) {
                    list.addAll(weatherList.value!!.toMutableList())
                }
                list.add(response.cityWeatherItem)
                weatherList.postValue(list)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}