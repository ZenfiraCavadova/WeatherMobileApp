package com.zenfira_cavadova.search

import androidx.lifecycle.ViewModel
import com.zenfira_cavadova.data.api.WeatherService
import com.zenfira_cavadova.domain.repositories.WeatherRepository

class SearchViewModel(
    private val weatherService:WeatherService,
    private val weatherRepository: WeatherRepository
): ViewModel(){


}