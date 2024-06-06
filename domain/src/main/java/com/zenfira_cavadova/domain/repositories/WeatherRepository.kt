package com.zenfira_cavadova.domain.repositories

import com.zenfira_cavadova.domain.entities.WeatherItem
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getAllWeatherItems(): Flow<List<WeatherItem>>
    suspend fun addWeatherItem(weatherItem: WeatherItem)
    suspend fun removeWeatherItem(weatherItem: WeatherItem)

    fun deleteWeatherItem(weatherItem: WeatherItem)
    fun getWeather(): Flow<List<WeatherItem>>
}