package com.zenfira_cavadova.data.repositories

import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.data.database.db.DatabaseManager
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.entities.response_models.GetAllWeatherResponseModels
import com.zenfira_cavadova.domain.repositories.WeatherRepository

class WeatherRepositoryImpl :WeatherRepository{
    override fun getAllWeatherItems(): List<WeatherItem> {
        return  DatabaseManager.database.weatherDao().getAllWeatherItems()
    }

    override suspend fun addWeatherItem(weatherItem: WeatherItem) {
        DatabaseManager.database.weatherDao().insertWeatherItem(weatherItem)
    }

    override suspend fun removeWeatherItem(weatherItem: WeatherItem) {
        DatabaseManager.database.weatherDao().deleteWeatherItem(weatherItem)
    }

}