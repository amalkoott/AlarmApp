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
import ru.amalkoott.alarmapp.data.service.StopwatchService
import ru.amalkoott.alarmapp.data.service.TimerService
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotification


object ForegroundServiceTools {
    fun NotificationManager.createNotificationChannel(companion: StopwatchService.Companion){
        this.createNotificationChannel(getNotificationChannel(id = companion.CHANNEL_ID,name = companion.CHANNEL_NAME))
    }
    fun NotificationManager.notify(companion: StopwatchService.Companion, context: Context, value: Any){
            val notification = getNotification(
            context,
            companion.CHANNEL_ID,
            value,
            companion.NOTIFICATION_ID
        )

        this.notify(companion.NOTIFICATION_ID,notification)
    }
    fun NotificationManager.notify(companion: TimerService.Companion, context: Context, value: Any){
        val notification = getNotification(
            context,
            companion.CHANNEL_ID,
            value,
            companion.NOTIFICATION_ID
        )

        this.notify(companion.NOTIFICATION_ID,notification)
    }

    fun NotificationManager.createNotificationChannel(companion: TimerService.Companion){
        this.createNotificationChannel(getNotificationChannel(id = companion.CHANNEL_ID,name = companion.CHANNEL_NAME))
    }

    fun getNotification(companion: StopwatchService.Companion, context: Context, value: Any): Notification{
        return buildNotification(context,companion.CHANNEL_ID,companion.NOTIFICATION_ID,value)
    }
    fun getNotification(companion: TimerService.Companion, context: Context, value: Any): Notification{
        return buildNotification(context,companion.CHANNEL_ID,companion.NOTIFICATION_ID,value)
    }


    private fun buildNotification(context: Context, channelName: String, notificationId: Int, value:Any): Notification{
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return getPermissionNotification(context,channelName,notificationId)
        }else{
            return getValueNotification(context,channelName,value)
        }
    }
    private fun getPermissionNotification(context: Context,channelName: String, id: Int):Notification{
        return NotificationCompat.Builder(context, channelName)
            .setNumber(id)
            .setContentText("Set permissions")
            .setContentTitle("please")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
    }
    private fun getValueNotification(context: Context,channelName: String, value: Any):Notification{
        return NotificationCompat.Builder(context, channelName)
            .setContentText("Update: $value")
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun getNotificationChannel(id: String, name: String): NotificationChannel{
        return NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Channel description"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 200, 100, 200)
        }
    }
}