package com.zenfira_cavadova.settings

import android.content.Context
import android.content.res.Configuration
import androidx.core.os.ConfigurationCompat
import java.util.Locale

object LocaleHelper {
    fun setLocale(context: Context, language:String):Context{
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config=Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun getLocale(context: Context): String {
        val locales = ConfigurationCompat.getLocales(context.resources.configuration)
        val locale= locales.get(0) ?: Locale.getDefault()
        return locale.language
    }
}