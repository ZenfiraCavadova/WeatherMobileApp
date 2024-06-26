package com.zenfira_cavadova.data.api

import com.zenfira_cavadova.domain.entities.response_models.GetAllWeatherResponseModels
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeatherForecast(
        @Query("q")
        cityName: String,
        @Query("appid")
        apiKey: String =NetworkManager.API_KEY
    ): GetAllWeatherResponseModels
}