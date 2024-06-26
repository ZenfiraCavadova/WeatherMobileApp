package com.zenfira_cavadova.weatherapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.zenfira_cavadova.settings.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        val defaultLanguage= LocaleHelper.getLocale(this)
        LocaleHelper.applyLocale(this,defaultLanguage)
    }
}