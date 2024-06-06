package com.zenfira_cavadova.domain.usecase

import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.repositories.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase  @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<WeatherItem>> {
        return weatherRepository.getAllWeatherItems()
    }
}