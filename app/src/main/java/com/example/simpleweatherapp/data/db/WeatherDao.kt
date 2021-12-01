package com.example.simpleweatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simpleweatherapp.data.db.entities.CityWeatherItem

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllWeather(quotes: List<CityWeatherItem>)

    @Query("SELECT * FROM CityWeatherItem")
    fun getWeatherList(): LiveData<List<CityWeatherItem>>

}