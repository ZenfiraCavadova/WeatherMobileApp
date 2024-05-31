package com.zenfira_cavadova.home

import com.zenfira_cavadova.domain.entities.WeatherItem

data class HomeState(
    val isLoading: Boolean,
    val weatherItems: List<WeatherItem>? =null,
    val weatherItem: WeatherItem? = null
)
