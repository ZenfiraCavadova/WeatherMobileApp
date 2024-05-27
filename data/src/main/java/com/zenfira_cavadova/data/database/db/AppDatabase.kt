package com.zenfira_cavadova.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.domain.entities.WeatherItem

@Database(entities = [WeatherItem::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun weatherDao():WeatherDao
}
