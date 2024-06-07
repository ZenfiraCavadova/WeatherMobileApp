package com.zenfira_cavadova.data.di

import com.zenfira_cavadova.data.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val API_KEY = "e469b79d53ebcaf1ec4a6d2f1223eb7f"
    private const val BASE_URL ="https://api.openweathermap.org/data/2.5/"
    private const val TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideHttpLogger() = HttpLoggingInterceptor().also { httpLoggingInterceptor ->
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    @Provides
    @Singleton
    fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return try {
            val builder = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesService(
        retrofit: Retrofit
    ): WeatherService = retrofit.create(WeatherService::class.java)
}
