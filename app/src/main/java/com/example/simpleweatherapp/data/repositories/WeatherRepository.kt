package com.example.simpleweatherapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleweatherapp.data.db.AppDatabase
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem
import com.example.simpleweatherapp.data.network.MyApi
import com.example.simpleweatherapp.data.network.SafeApiRequest
import com.example.simpleweatherapp.data.network.responses.CityWeatherResponse
import com.example.simpleweatherapp.data.preferences.PreferenceProvider
import com.example.simpleweatherapp.util.ApiConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val weatherList = MutableLiveData<List<CityWeatherItem>>()
    private var cityWeatherItem = MutableLiveData<CityWeatherItem>()
    private val errorException = MutableLiveData<String>()

    init {
        weatherList.postValue(mutableListOf())
    }

    fun initCityWeatherItem() {
        cityWeatherItem = MutableLiveData<CityWeatherItem>()
    }

    fun getWeatherList(): LiveData<List<CityWeatherItem>> {
        return weatherList
    }

    fun getWeatherItem(): LiveData<CityWeatherItem> {
        return cityWeatherItem
    }

    fun getErrorException(): LiveData<String> {
        return errorException
    }

    suspend fun addNewCity(cityName: String) {
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    apiRequest { api.getCityWeather(cityName, ApiConstant.APP_ID) }
                val list = mutableListOf<CityWeatherItem>()
                if (weatherList.value?.isNotEmpty() == true) {
                    list.addAll(weatherList.value!!.toMutableList())
                }
                val item = mapResponse(response)
                list.add(item)
                weatherList.postValue(list)
                cityWeatherItem.postValue(item)
            } catch (e: Exception) {
                errorException.postValue(e.toString())
                e.printStackTrace()
            }

        }
    }

    private fun mapResponse(response: CityWeatherResponse): CityWeatherItem {
        val cityWeatherItem = CityWeatherItem()
        cityWeatherItem.cityName = response.name
        cityWeatherItem.weatherInfo = response.name
        cityWeatherItem.description = response.weather?.get(0)?.description
        cityWeatherItem.temp = response.main?.temp.toString()
        cityWeatherItem.humidity = response.main?.humidity.toString()
        cityWeatherItem.windSpeed = response.wind?.speed.toString()
        cityWeatherItem.weatherImage =
            ApiConstant.BASE_URL + "img/w/" + response.weather?.get(0)?.icon +
                    ".png?appid=" + ApiConstant.APP_ID
        response.wind?.speed.toString()
        return cityWeatherItem
    }

}