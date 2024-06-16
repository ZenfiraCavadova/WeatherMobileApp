package com.zenfira_cavadova.settings.workmanagers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zenfira_cavadova.core.NotificationHelper
import com.zenfira_cavadova.domain.usecase.GetWeatherUseCase
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class UploadWorker @AssistedInject constructor(context: Context,workerParams: WorkerParameters, private val getWeatherUseCase: GetWeatherUseCase)
    : CoroutineWorker(context, workerParams) {
//    @Inject lateinit

    override suspend fun doWork(): Result {
        val weatherData=getWeatherUseCase()
        NotificationHelper.showNotification(applicationContext, "Weather Update", "Current weather: $weatherData")
        uploadImages()
        return Result.success()
    }

    private suspend fun uploadImages(){
        delay(2000)
        Log.e("Work_Manager","Work Manager is working")
    }
}