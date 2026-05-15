package com.example.safemindsmobile.utils

import android.Manifest
import com.example.safemindsmobile.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {
    private const val CHANNEL_ID = "risk_alert_channel"
    private const val CHANNEL_NAME = "Risk Alerts"

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        val channel= NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH

        )
            .apply {
                description="Alerts for high risk conditions"
            }

        val manager=context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }


    fun showHighRiskNotification(context: Context, title: String, score: Int) {
        if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )!=
            PackageManager.PERMISSION_GRANTED
        ){
            return
        }

        val notification= NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("High risk alert!")
            .setContentText("Your latest CSI score is $score.Please review your risk analysis")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1001, notification)


    }



}