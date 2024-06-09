package com.zenfira_cavadova.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.domain.usecase.GetWeatherUseCase
import com.zenfira_cavadova.domain.usecase.RemoveWeatherUseCase
import com.zenfira_cavadova.settings.workmanagers.UploadWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getWeatherItemsUseCase: GetWeatherUseCase,
    private val removeWeatherUseCase: RemoveWeatherUseCase
) :BaseViewModel<SettingsState,SettingsEffect,SettingsEvent>() {
    private val _temperatureUnit= MutableStateFlow("K")
    val temperatureUnit:StateFlow<String> get()=_temperatureUnit

    private val _windSpeedUnit=MutableStateFlow("mph")
    val windSpeedUnit:StateFlow<String> get()=_windSpeedUnit

    private val _language=MutableStateFlow("English")
    val language:StateFlow<String> get()=_language

    private val _updateWeather=MutableStateFlow(false)
    val updateWeather:StateFlow<Boolean> get()=_updateWeather

    private lateinit var sharedPreferences: SharedPreferences
    private var weatherUnitUpdateListener: WeatherUnitUpdateListener? = null

    init {
        viewModelScope.launch {
            _temperatureUnit.collect { newTempUnit ->
                weatherUnitUpdateListener?.updateUnits(newTempUnit, windSpeedUnit.value)
            }

            _windSpeedUnit.collect { newWindSpeedUnit ->
                weatherUnitUpdateListener?.updateUnits(windSpeedUnit.value, newWindSpeedUnit)
            }
        }
    }

    fun setHomeFragmentListener(listener: WeatherUnitUpdateListener) {
        weatherUnitUpdateListener = listener
    }
     fun loadSettings(sharedPreferences: SharedPreferences) {
        this.sharedPreferences=sharedPreferences
      viewModelScope.launch(Dispatchers.IO){
          _temperatureUnit.value=sharedPreferences.getString("temperature_unit","K") ?:"K"
          _windSpeedUnit.value=sharedPreferences.getString("wind_speed_unit","mph") ?:"mph"
          _language.value=sharedPreferences.getString("language","English") ?:"English"
          _updateWeather.value=sharedPreferences.getBoolean("update_weather",false)
      }
    }

    fun setTemperatureUnit(unit:String){
            _temperatureUnit.value = unit
            sharedPreferences.edit().putString("temperature_unit", unit).apply()

    }

    fun setWindSpeedUnit(unit:String){
            _windSpeedUnit.value = unit
            sharedPreferences.edit().putString("wind_speed_unit", unit).apply()
    }

    fun setLanguage(lang:String){
            _language.value = lang
            sharedPreferences.edit().putString("language", lang).apply()
    }

    fun setUpdateWeather(update:Boolean){
            _updateWeather.value = update
            sharedPreferences.edit().putBoolean("update_weather", update).apply()
    }

    fun schedulePeriodicWeatherUpdate(context: Context, enable:Boolean){
        val workManager=WorkManager.getInstance(context)
        val periodicWorkRequest= PeriodicWorkRequestBuilder<UploadWorker>(24,TimeUnit.HOURS).build()

        if (enable){
            workManager.enqueueUniquePeriodicWork(
                "WeatherUpdateWork",
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicWorkRequest
            )
        }
        else{
            workManager.cancelUniqueWork("WeatherUpdateWork")
        }
    }

    override fun getInitialState(): SettingsState {
        return SettingsState(isLoading = false)
    }
    companion object{
        fun create(sharedPreferences: SharedPreferences, getWeatherItemsUseCase: GetWeatherUseCase,
                   removeWeatherUseCase: RemoveWeatherUseCase):SettingsViewModel{
            return SettingsViewModel(getWeatherItemsUseCase,removeWeatherUseCase).apply {
                loadSettings(sharedPreferences)
            }
        }
    }
}
