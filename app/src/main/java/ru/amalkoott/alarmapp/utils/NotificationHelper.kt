package ru.amalkoott.alarmapp.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import ru.amalkoott.alarmapp.R

object NotificationHelper{
    fun getNotificationChannel(id: String, name: String): NotificationChannel{
        val channelId = id
        val channelName = name
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel description"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 200, 100, 200)
        }
        return channel
    }

    fun getNotification(context: Context, channelName: String, value: Any = "", number: Int) : Notification {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            val notification = NotificationCompat.Builder(context, channelName)
                .setNumber(number)
                .setContentText("Set permissions")
                .setContentTitle("please")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true) // an ongoing notification means can't dismiss by the user.
                .setOnlyAlertOnce(true)
                .build()

            return notification
        }
        val notification = NotificationCompat.Builder(context, channelName)
            .setContentText("Update: $value")
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
        return notification
    }
    fun getNotification(context: Context, channelName: String, seconds: Any = "", milliseconds: Any ="", number: Int) : Notification {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            val notification = NotificationCompat.Builder(context, channelName)
                .setNumber(number)
                .setContentText("Set permissions")
                .setContentTitle("please")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true) // an ongoing notification means can't dismiss by the user.
                .setOnlyAlertOnce(true)
                .build()

            return notification
        }
        val notification = NotificationCompat.Builder(context, channelName)
            .setContentText("Update: $seconds $milliseconds")
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
        return notification
    }
}
