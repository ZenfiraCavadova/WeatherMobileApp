package com.zenfira_cavadova.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.data.api.NetworkManager
import com.zenfira_cavadova.data.api.WeatherService
import com.zenfira_cavadova.data.repositories.WeatherRepositoryImpl
import com.zenfira_cavadova.domain.entities.WeatherItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<HomeState,HomeEffect,HomeEvent>() {
    private val weatherRepository = WeatherRepositoryImpl()
    private val _weatherItemsFlow= MutableStateFlow<List<WeatherItem>>(emptyList())
    val weatherItemsFlow: Flow<List<WeatherItem>> = _weatherItemsFlow
    private val weatherService: WeatherService by lazy { NetworkManager.getWeatherServiceInstance() }

    init {
        viewModelScope.launch(Dispatchers.IO){
            weatherRepository.getAllWeatherItems().
            onEach { currentDatabaseValue ->
                setState(
                    getCurrentState()
                        .copy(
                            weatherItem = currentDatabaseValue
                        )
                )
            }
        }
//            }.launchIn(viewModelScope)
        fetchAllWeatherItems()
    }
    fun addWeatherItem(weatherItem: WeatherItem){
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.addWeatherItem(weatherItem)
            fetchAllWeatherItems()
        }
    }

    fun fetchAllWeatherItems(){
        viewModelScope.launch(Dispatchers.IO){
            val weatherItems= weatherRepository.getAllWeatherItems()
            _weatherItemsFlow.emit(weatherItems)
        }
    }
    fun fetchWeatherForCity(cityName:String){
        viewModelScope.launch {
            try {
                val response=weatherService.getWeatherForecast(cityName)
                Log.e("WeatherAPI", "Response: $response")
                val weatherItem= WeatherItem(
                    temperature = response.main.temperature?.toString() ?: "N/A",
                    highAndLowTemp = "H:${response.main.tepMax} L:${response.main.tepMin}",
                    location = response.location,
                    weatherIcon = response.weather[0].icon ?:0,
                    weatherDescription = response.weather[0].description,
                    windSpeed = response.wind.speed.toString()
                )
                addWeatherItem(weatherItem)
                fetchAllWeatherItems()
            }catch (e:Exception){
                Log.e("WeatherAPI", "Error fetching weather data", e)
            }
        }
    }

    override fun getInitialState(): HomeState = HomeState(isLoading = false)
}