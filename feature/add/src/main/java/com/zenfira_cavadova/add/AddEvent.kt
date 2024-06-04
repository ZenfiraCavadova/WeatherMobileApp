package com.zenfira_cavadova.add

sealed class AddEvent {
    data class SaveWeather(val temperature: String,
                           val highAndLowTemp: String,
                           val location: String,
                           val weatherIcon: Int,
                           val weatherDescription: String,
                           val windSpeed:String):AddEvent()
}