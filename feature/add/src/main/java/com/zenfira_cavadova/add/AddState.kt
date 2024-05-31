package com.zenfira_cavadova.add

import com.zenfira_cavadova.domain.entities.WeatherItem

data class AddState(
    val isLoading: Boolean,
    val weatherItems: List<WeatherItem>? =null,
    val weatherItem: WeatherItem? = null
)
