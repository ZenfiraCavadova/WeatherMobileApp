package com.zenfira_cavadova.weatherapp

import android.app.Application
import com.google.firebase.FirebaseApp

class WeatherApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}