package com.zenfira_cavadova.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    var retrofit: Retrofit

    const val API_KEY = "e469b79d53ebcaf1ec4a6d2f1223eb7f"
    private const val BASE_URL ="https://api.openweathermap.org/data/2.5/"
//        "api.openweathermap.org/data/2.5/forecast/daily?q={city name}&cnt={cnt}&appid=${API_KEY}"
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