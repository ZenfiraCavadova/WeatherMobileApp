package com.zenfira_cavadova.domain.usecase

import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.repositories.WeatherRepository
import javax.inject.Inject

class RemoveWeatherUseCase  @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(weatherItem: WeatherItem) {
        return weatherRepository.removeWeatherItem(weatherItem)
    }
}