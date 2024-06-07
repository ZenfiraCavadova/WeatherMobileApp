package com.zenfira_cavadova.data.database.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                CREATE TABLE weather_database_new (
                    temperature TEXT NOT NULL,
                    highLow TEXT NOT NULL,
                    location TEXT NOT NULL,
                    weatherIcon TEXT NOT NULL,
                    weatherDescription TEXT NOT NULL,
                    windSpeed TEXT NOT NULL,
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL
                )
            """)

            database.execSQL("""
                INSERT INTO weather_database_new (temperature, highLow, location, weatherIcon, weatherDescription, windSpeed, id)
                SELECT temperature, highLow, location, CAST(weatherIcon AS TEXT), weatherDescription, windSpeed, id FROM weather_database
            """)

            database.execSQL("DROP TABLE weather_database")

            database.execSQL("ALTER TABLE weather_database_new RENAME TO weather_database")
        }
    }
}