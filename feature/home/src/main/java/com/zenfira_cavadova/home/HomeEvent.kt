package com.zenfira_cavadova.home

sealed class HomeEvent {
    data class FetchWeather(val cityName:String):HomeEvent()
}