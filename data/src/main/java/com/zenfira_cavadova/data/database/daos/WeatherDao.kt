package com.zenfira_cavadova.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zenfira_cavadova.domain.entities.WeatherItem
import com.zenfira_cavadova.domain.entities.response_models.GetAllWeatherResponseModels

@Dao
interface WeatherDao {
    @Query("SELECT * FROM 'weather_database'")
    fun getAllWeatherItems():List<WeatherItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherItem(weatherItem: WeatherItem)

    @Delete
    fun deleteWeatherItem(weatherItem: WeatherItem)
}