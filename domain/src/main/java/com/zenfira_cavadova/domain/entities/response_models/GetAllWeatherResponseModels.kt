package com.zenfira_cavadova.domain.entities.response_models

import com.google.gson.annotations.SerializedName

data class GetAllWeatherResponseModels(
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("highLow")
    val highAndLowTemp: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("weatherIcon")
    val weatherIcon: Int,
    @SerializedName("weatherDescription")
    val weatherDescription: String
)