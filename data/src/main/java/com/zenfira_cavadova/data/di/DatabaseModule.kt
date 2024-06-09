package com.zenfira_cavadova.data.di

import android.content.Context
import androidx.room.Room
import com.zenfira_cavadova.data.database.daos.WeatherDao
import com.zenfira_cavadova.data.database.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "weather_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(
        appDatabase: AppDatabase
    ): WeatherDao {
        return appDatabase.weatherDao()
    }
}