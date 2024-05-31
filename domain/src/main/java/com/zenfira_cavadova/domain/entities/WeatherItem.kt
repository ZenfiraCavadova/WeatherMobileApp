package com.zenfira_cavadova.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  (tableName = "weather_database")
data class WeatherItem(
    @ColumnInfo("temperature")
    val temperature: String,
    @ColumnInfo("highLow")
    val highAndLowTemp: String,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("weatherIcon")
    val weatherIcon: Int,
    @ColumnInfo("weatherDescription")
    val weatherDescription: String,
    @ColumnInfo("windSpeed")
    val windSpeed:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
)
