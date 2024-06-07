package com.zenfira_cavadova.data.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.domain.entities.WeatherItem

@Database(entities = [WeatherItem::class], version = 2)
abstract class AppDatabase:RoomDatabase() {
    abstract fun weatherDao():WeatherDao
    companion object {
        val MIGRATION_1_2: Migration = Migrations.MIGRATION_1_2
        private const val DATABASE_NAME = "app_database.db"
    }
}
