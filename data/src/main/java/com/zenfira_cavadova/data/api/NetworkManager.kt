package com.zenfira_cavadova.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    var retrofit: Retrofit

    private const val API_KEY = ""
    private const val BASE_URL =
        "api.openweathermap.org/data/2.5/forecast/daily?q={city name}&cnt={cnt}&appid={API key}"
    private const val TIMEOUT = 30L

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getWeatherServiceInstance(): WeatherService = retrofit.create(WeatherService::class.java)
}