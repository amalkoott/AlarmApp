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
import ru.amalkoott.alarmapp.domain.ForegroundCompanion


object ForegroundServiceTools {
    fun NotificationManager.createNotificationChannel(companion: ForegroundCompanion){
        val channel = NotificationChannel(companion.CHANNEL_ID, companion.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
            description = companion.CHANNEL_DESCRIPTION//"Channel description"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 200, 100, 200)
        }

        this.createNotificationChannel(channel)
    }

    fun NotificationManager.notify(companion: ForegroundCompanion, context: Context, value: Any){
        val notification = getNotification(
            companion,
            context,
            value
        )

        this.notify(companion.NOTIFICATION_ID,notification)
    }
    fun getNotification(companion: ForegroundCompanion, context: Context, value: Any): Notification{
        return if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            getPermissionNotification(context,companion)
        }else{
            getValueNotification(context,companion,value)
        }
    }

    private fun getPermissionNotification(context: Context, companion: ForegroundCompanion):Notification{
        return NotificationCompat.Builder(context, companion.CHANNEL_ID)
            .setNumber(companion.NOTIFICATION_ID)
            .setContentText(companion.NOTIFICATION_PERMISSION_REQUEST)
            .setContentTitle(companion.NOTIFICATION_PERMISSION_TITLE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
    }
    private fun getValueNotification(context: Context, companion: ForegroundCompanion, value: Any):Notification{
        return NotificationCompat.Builder(context, companion.CHANNEL_ID)
            .setContentText("$value")
            .setContentTitle(companion.NOTIFICATION_TITLE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
    }


}