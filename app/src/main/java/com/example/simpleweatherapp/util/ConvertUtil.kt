package com.example.simpleweatherapp.util

class ConvertUtil {
    companion object {
        fun convertFromKelvinToCelsius(kelvin: Double): Double {
            return kelvin.minus(273.15)
        }
    }
}