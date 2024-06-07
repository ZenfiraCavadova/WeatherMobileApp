package com.zenfira_cavadova.add

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.data.api.NetworkManager
import com.zenfira_cavadova.data.api.WeatherService
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.usecase.AddWeatherUseCase
import com.zenfira_cavadova.domain.usecase.GetWeatherUseCase
import com.zenfira_cavadova.domain.usecase.RemoveWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAndRemoveViewModel @Inject constructor(
    getWeatherItemsUseCase: GetWeatherUseCase,
    private val addWeatherUseCase: AddWeatherUseCase,
    private val removeWeatherUseCase: RemoveWeatherUseCase
) :BaseViewModel<AddState,AddEffect,AddEvent>() {
    private val _weatherItemsFlow= MutableStateFlow<List<WeatherItem>>(emptyList())
    val weatherItemsFlow:Flow<List<WeatherItem>> = _weatherItemsFlow
//    private val weatherRepository:WeatherRepository by lazy { WeatherRepositoryImpl() }
    private val weatherService:WeatherService by lazy { NetworkManager.getWeatherServiceInstance() }

    init {
        getWeatherItemsUseCase()
            .onEach {currentDatabaseValue->
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

    fun removeWeatherItem(weatherItem: WeatherItem){
        viewModelScope.launch(Dispatchers.IO){
            removeWeatherUseCase(weatherItem)
        }
    }

    override  fun onEventUpdate(event: AddEvent) {
        when (event){
            is AddEvent.SaveWeather ->saveWeather(
                event.location,event.highAndLowTemp,event.temperature,
                event.weatherIcon.toString(),event.weatherDescription, event.windSpeed
            )
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
        postEffect(AddEffect.OnWeatherAdded)
    }

    fun fetchWeatherForCity(cityName:String, onResult: (WeatherItem?)->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response=weatherService.getWeatherForecast(cityName)
                Log.e("WeatherAPI", "Response: $response")
                val weatherItem=WeatherItem(
                    temperature = response.main.temp?.toString() ?: "N/A",
                    highAndLowTemp = "H:${response.main.tepMax} L:${response.main.tepMin}",
                    location = response.location,
                    weatherIcon = response.weather[0].icon ?:"",
                    weatherDescription = response.weather[0].description,
                    windSpeed = response.wind.speed.toString()
                )
                onResult(weatherItem)
                addWeatherItem(weatherItem)
                _weatherItemsFlow.emit(listOf(weatherItem))
            }catch (e:Exception){
                Log.e("WeatherAPI", "Error fetching weather data", e)
                onResult(null)
            }
        }
    }
    override fun getInitialState(): AddState {
        return AddState(isLoading = false, weatherItems = emptyList())
    }
}