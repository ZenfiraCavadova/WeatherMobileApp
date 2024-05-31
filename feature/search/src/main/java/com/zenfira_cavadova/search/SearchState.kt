package com.zenfira_cavadova.search

import com.zenfira_cavadova.domain.entities.WeatherItem

data class SearchState(
    val isLoading: Boolean,
    val weatherItems: List<WeatherItem>? =null,
    val weatherItem: WeatherItem? = null
)
