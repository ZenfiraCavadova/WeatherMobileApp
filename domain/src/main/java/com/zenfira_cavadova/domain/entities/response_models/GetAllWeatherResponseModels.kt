package com.zenfira_cavadova.domain.entities.response_models

import com.google.gson.annotations.SerializedName

data class GetAllWeatherResponseModels(
    @SerializedName("name")
    val location:String,
    @SerializedName("main")
    val main:Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind:Wind
){
    data class Main(
        @SerializedName("temperature")
        val temp: Double?,
        @SerializedName("temp_min")
        val tepMin:Double,
        @SerializedName("temp_max")
        val tepMax:Double
    )

    data class Weather(
        @SerializedName("description")
        val description:String,
        @SerializedName("icon")
        val icon:String
    )

    data class Wind(
        @SerializedName("speed")
        val speed:Double
    )
}