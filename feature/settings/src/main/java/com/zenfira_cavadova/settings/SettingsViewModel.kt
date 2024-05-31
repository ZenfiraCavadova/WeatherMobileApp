package com.zenfira_cavadova.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel():BaseViewModel<SettingsState,SettingsEffect,SettingsEvent>() {
    private val _temperatureUnit= MutableStateFlow("C")
    val temperatureUnit:StateFlow<String> get()=_temperatureUnit

    private val _windSpeedUnit=MutableStateFlow("km/h")
    val windSpeedUnit:StateFlow<String> get()=_windSpeedUnit

    private val _language=MutableStateFlow("English")
    val language:StateFlow<String> get()=_language

    private val _updateWeather=MutableStateFlow(false)
    val updateWeather:StateFlow<Boolean> get()=_updateWeather

    private lateinit var sharedPreferences: SharedPreferences

//    init {
//        loadSettings(sharedPreferences)
//    }

     fun loadSettings(sharedPreferences: SharedPreferences) {
        this.sharedPreferences=sharedPreferences
      viewModelScope.launch(Dispatchers.IO){
          _temperatureUnit.value=sharedPreferences.getString("temperature_unit","C") ?:"C"
          _windSpeedUnit.value=sharedPreferences.getString("wind_speed_unit","km/h") ?:"km/h"
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
            _windSpeedUnit.value = lang
            sharedPreferences.edit().putString("language", lang).apply()
    }

    fun setUpdateWeather(update:Boolean){
            _updateWeather.value = update
            sharedPreferences.edit().putBoolean("update_weather", update).apply()
    }

    override fun getInitialState(): SettingsState {
        return SettingsState(isLoading = false)
    }
    companion object{
        fun create(sharedPreferences: SharedPreferences):SettingsViewModel{
            return SettingsViewModel().apply {
                loadSettings(sharedPreferences)
            }
        }
    }
}
