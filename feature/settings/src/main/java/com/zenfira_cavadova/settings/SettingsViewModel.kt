package com.zenfira_cavadova.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val sharedPreferences: SharedPreferences):ViewModel() {
    private val _temperatureUnit=MutableLiveData<String>()
    val temperatureUnit:LiveData<String> get()=_temperatureUnit

    private val _windSpeedUnit=MutableLiveData<String>()
    val windSpeedUnit:LiveData<String> get()=_windSpeedUnit

    private val _language=MutableLiveData<String>()
    val language:LiveData<String> get()=_language

    private val _updateWeather=MutableLiveData<Boolean>()
    val updateWeather:LiveData<Boolean> get()=_updateWeather

    init {
        loadSettings()
    }

    private fun loadSettings() {
        _temperatureUnit.value=sharedPreferences.getString("temperature_unit","C")
        _windSpeedUnit.value=sharedPreferences.getString("wind_speed_unit","km/h")
        _language.value=sharedPreferences.getString("language","English")
        _updateWeather.value=sharedPreferences.getBoolean("update_weather",false)

    }

    fun setTemperatureUnit(unit:String){
        _temperatureUnit.value=unit
        sharedPreferences.edit().putString("temperature_unit",unit).apply()
    }

    fun setWindSpeedUnit(unit:String){
        _windSpeedUnit.value=unit
        sharedPreferences.edit().putString("wind_speed_unit",unit).apply()
    }

    fun setLanguage(lang:String){
        _windSpeedUnit.value=lang
        sharedPreferences.edit().putString("language",lang).apply()
    }

    fun setUpdateWeather(update:Boolean){
        _updateWeather.value=update
        sharedPreferences.edit().putBoolean("update_weather",update).apply()
    }



}
