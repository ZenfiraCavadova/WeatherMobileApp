package com.zenfira_cavadova.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.data.api.NetworkManager
import com.zenfira_cavadova.data.repositories.WeatherRepositoryImpl
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.entities.response_models.GetAllWeatherResponseModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(): BaseViewModel<SearchState,SearchEffect,SearchEvent>() {
    private val weatherRepository = WeatherRepositoryImpl()
    private val _weatherData = MutableStateFlow<GetAllWeatherResponseModels?>(null)
    val weatherData: StateFlow<GetAllWeatherResponseModels?> get() = _weatherData

    fun fetchWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.e("API Request", "Requesting weather for: $city with API key: ${NetworkManager.API_KEY}")
                val weatherResponse = NetworkManager.getWeatherServiceInstance().getWeatherForecast(
                    city,
                    NetworkManager.API_KEY
                )
                _weatherData.value = weatherResponse
            } catch (e: Exception) {
                Log.e("API error","ERRRORRRR", e)
            }

        }
    }

    fun addWeatherItem(weatherItem: WeatherItem) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.addWeatherItem(weatherItem)
        }
    }

    fun removeWeatherItem(weatherItem: WeatherItem) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.removeWeatherItem(weatherItem)
        }
    }

    fun getAllWeatherItems(): StateFlow<List<WeatherItem>> {
        val weatherItemsFLow = MutableStateFlow<List<WeatherItem>>(emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            val weatherItems = weatherRepository.getAllWeatherItems()
            weatherItemsFLow.value=weatherItems.map {responseModel ->
                WeatherItem(
                    temperature = responseModel.temperature ?: "0.0",
                    highAndLowTemp = responseModel.highAndLowTemp ?: "",
                    location = responseModel.location ?: "",
                    weatherIcon = responseModel.weatherIcon ?: 0,
                    weatherDescription = responseModel.weatherDescription ?: "",
                    windSpeed = responseModel.windSpeed ?: ""
                )
            }
        }
        return weatherItemsFLow
    }

    override fun getInitialState(): SearchState {
        return SearchState(isLoading = false)
    }
}

