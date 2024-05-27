package com.zenfira_cavadova.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.data.repositories.WeatherRepositoryImpl
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAndRemoveViewModel(private val weatherDao: WeatherDao):ViewModel() {

    private val weatherRepository:WeatherRepository by lazy { WeatherRepositoryImpl() }

    fun getAllWeatherItems()=weatherRepository.getAllWeatherItems()

    fun addWeatherItem(weatherItem: WeatherItem){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.addWeatherItem(weatherItem)
        }
    }

    fun removeWeatherItem(weatherItem: WeatherItem){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepository.removeWeatherItem(weatherItem)
        }
    }
}