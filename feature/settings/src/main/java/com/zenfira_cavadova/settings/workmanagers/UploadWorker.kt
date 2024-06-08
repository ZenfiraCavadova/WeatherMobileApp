package com.zenfira_cavadova.settings.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zenfira_cavadova.core.NotificationHelper
import kotlinx.coroutines.delay

class UploadWorker(context: Context, workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        NotificationHelper.showNotification(applicationContext, "Weather Update", "Current weather")
        uploadImages()
        return Result.success()
    }

    private suspend fun uploadImages(){
        delay(2000)
        Log.e("Work_Manager","Work Manager is working")
    }
}