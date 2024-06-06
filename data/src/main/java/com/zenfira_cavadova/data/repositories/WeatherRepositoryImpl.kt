package com.zenfira_cavadova.data.repositories

import com.zenfira_cavadova.data.api.WeatherService
import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.data.database.db.DatabaseManager
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) :WeatherRepository{
    override fun getAllWeatherItems(): Flow<List<WeatherItem>> {
        return  DatabaseManager.database.weatherDao().getAllWeatherItems()
    }

    override suspend fun addWeatherItem(weatherItem: WeatherItem) {
        DatabaseManager.database.weatherDao().insertWeatherItem(weatherItem)
    }

    override suspend fun removeWeatherItem(weatherItem: WeatherItem) {
        DatabaseManager.database.weatherDao().deleteWeatherItem(weatherItem)
    }


    override fun getWeather(): Flow<List<WeatherItem>> {
        return weatherDao.getAllWeatherItems()
    }
    override fun deleteWeatherItem(weatherItem: WeatherItem) {
        weatherDao.deleteWeatherItem(weatherItem)
    }
}