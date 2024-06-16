package com.zenfira_cavadova.core

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

object NotificationHelper {
    private const val CHANNEL_ID="Weather Channel ID"
    fun showNotification(context: Context, title:String,message:String) {

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_weather)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        var notificationManager: NotificationManager? =null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Message"
            val descriptionText = "Weather Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText
            notificationManager =
                ContextCompat.getSystemService(context, NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
            builder.setChannelId(CHANNEL_ID)
        }
        notificationManager?.notify(1234, builder.build())
    }
}