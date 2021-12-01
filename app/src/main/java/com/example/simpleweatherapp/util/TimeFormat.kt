package com.example.simpleweatherapp.util

import java.text.SimpleDateFormat
import java.util.*

class TimeFormat {
    companion object {
        fun getCurrentDate(): String {
            val timeFormat = SimpleDateFormat("dd-MM-yyyy - HH:mm")
            return timeFormat.format(Date())
        }
    }
}