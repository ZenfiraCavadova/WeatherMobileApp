package com.zenfira_cavadova.domain.repositories

import com.zenfira_cavadova.domain.entities.WeatherItem

interface WeatherRepository {
    fun getAllWeatherItems():List<WeatherItem>
    suspend fun addWeatherItem(weatherItem: WeatherItem)
    suspend fun removeWeatherItem(weatherItem: WeatherItem)
}