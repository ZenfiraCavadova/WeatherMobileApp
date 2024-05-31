package com.zenfira_cavadova.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenfira_cavadova.core.BaseViewModel
import com.zenfira_cavadova.domain.entities.WeatherItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel:BaseViewModel<DetailsState,DetailsEffect,DetailsEvent>() {
    private val _weatherItem= MutableStateFlow<WeatherItem?>(null)
    val weatherItem: StateFlow<WeatherItem?>get() = _weatherItem

    fun setWeatherItem(item: WeatherItem){
        viewModelScope.launch {
            _weatherItem.value= item
        }
    }

    override fun getInitialState(): DetailsState {
        return DetailsState(isLoading = false)
    }
}