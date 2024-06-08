package com.zenfira_cavadova.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.data.api.NetworkManager
import com.zenfira_cavadova.data.api.WeatherService
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.usecase.AddWeatherUseCase
import com.zenfira_cavadova.domain.usecase.GetWeatherUseCase
import com.zenfira_cavadova.domain.usecase.RemoveWeatherUseCase
import com.zenfira_cavadova.settings.SettingsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt


@HiltViewModel
class HomeViewModel @Inject constructor(
    getWeatherItemsUseCase: GetWeatherUseCase,
    private val addWeatherUseCase: AddWeatherUseCase,
    private val removeWeatherUseCase: RemoveWeatherUseCase
)  : BaseViewModel<HomeState,HomeEffect,HomeEvent>() {
    private lateinit var settingsViewModel: SettingsViewModel
//    private val weatherRepository = WeatherRepositoryImpl()
    private val _weatherItemsFlow= MutableStateFlow<List<WeatherItem>>(emptyList())
    val weatherItemsFlow: Flow<List<WeatherItem>> = _weatherItemsFlow
    private val weatherService: WeatherService by lazy { NetworkManager.getWeatherServiceInstance() }

    init {
        getWeatherItemsUseCase().onEach {currentDatabaseValue->
                   setState(
                   getCurrentState()
                       .copy(
                           weatherItems = currentDatabaseValue
                       )
               )
               }.launchIn(viewModelScope)

    }
    fun addWeatherItem(weatherItem: WeatherItem){
        viewModelScope.launch(Dispatchers.IO) {
            addWeatherUseCase(weatherItem)
        }
    }

    fun saveWeather(temperature: String, highAndLowTemp: String,location: String, weatherIcon: String, weatherDescription: String, windSpeed:String){
        val weatherItem=WeatherItem(
            temperature = temperature,
            highAndLowTemp = highAndLowTemp,
            location =location,
            weatherIcon = weatherIcon,
            weatherDescription = weatherDescription,
            windSpeed =windSpeed
        )
        viewModelScope.launch(Dispatchers.IO) {
            addWeatherUseCase(weatherItem)
        }
        addWeatherItem(weatherItem)
        postEffect(HomeEffect.OnWeatherAdded)
    }
    fun fetchWeatherForCity(cityName:String){
        viewModelScope.launch {
            try {
                val response=weatherService.getWeatherForecast(cityName)
                Log.e("WeatherAPI", "Response: $response")
                val temperatureUnit = settingsViewModel.temperatureUnit.value
                val windSpeedUnit = settingsViewModel.windSpeedUnit.value

                Log.e("WeatherAPI", "Main: ${response.main}")
                val tempInKelvin =response.main.temp ?: 0.0
                val highTempInKelvin =response.main.tempMax ?:0.0
                val lowTempInKelvin=response.main.tempMin ?:0.0
                val windSpeedInMetersPerSec=response.wind.speed ?: 0.0
                val highTemp = when(temperatureUnit){
                    "K"->"${highTempInKelvin.roundToInt()}"
                    "C"->"${(highTempInKelvin-273.15).roundToInt()}"
                    "F"->"${((highTempInKelvin - 273.15) * 9 / 5 + 32).roundToInt()}°F"
                    else ->"N/A"
                }
                val lowTemp = when(temperatureUnit){
                    "K"->"${lowTempInKelvin.roundToInt()} K"
                    "C"->"${(lowTempInKelvin-273.15).roundToInt()} °C"
                    "F"->"${((lowTempInKelvin - 273.15) * 9 / 5 + 32).roundToInt()}°F"
                    else ->"N/A"
                }
                val weatherItem= WeatherItem(
                    temperature = when(temperatureUnit){
                        "K"->"${tempInKelvin.roundToInt()} K"
                        "C"->"${(tempInKelvin-273.15).roundToInt()} °C"
                        "F"->"${((tempInKelvin - 273.15) * 9 / 5 + 32).roundToInt()}°F"
                        else ->"N/A"
                                                       },

                    highAndLowTemp = "H:$highTemp L:$lowTemp",
                    location = response.location,
                    weatherIcon = response.weather[0].icon ?:"",
                    weatherDescription = response.weather[0].description,
                    windSpeed = when(windSpeedUnit){
                        "mph"->"${(windSpeedInMetersPerSec* 2.23694).roundToInt()} mph"
                        "km/h" ->"${(windSpeedInMetersPerSec*3.6).roundToInt()} km/h"
                        else ->"N/A"
                    }
                )
                _weatherItemsFlow.emit(listOf(weatherItem))
            }catch (e:Exception){
                Log.e("WeatherAPI", "Error fetching weather data", e)
            }
        }
    }

    override fun getInitialState(): HomeState = HomeState(isLoading = false)
}