package com.zenfira_cavadova.home

sealed class HomeEvent {
    data class SaveWeather(val temperature: String,
                           val highAndLowTemp: String,
                           val location: String,
                           val weatherIcon: Int,
                           val weatherDescription: String,
                           val windSpeed:String):HomeEvent()
    data class FetchWeather(val cityName:String):HomeEvent()
}