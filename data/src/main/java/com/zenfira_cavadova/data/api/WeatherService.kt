package com.zenfira_cavadova.data.api

import com.zenfira_cavadova.domain.entities.response_models.GetAllWeatherResponseModels
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast/daily")
    suspend fun getWeatherForecast(
        @Query("q")
        cityName: String,
        @Query("cnt") count: Int,
        @Query("appid") apiKey: String
    ): GetAllWeatherResponseModels
}