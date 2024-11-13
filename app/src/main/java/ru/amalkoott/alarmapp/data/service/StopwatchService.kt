package ru.amalkoott.alarmapp.data.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ServiceCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotification
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotificationChannel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class StopwatchService : Service(){
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val binder = StopwatchBinder()
    private val _seconds = MutableStateFlow(0L)
    private val _milliseconds = MutableStateFlow(0)

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE_EXIT","Exit...")

    }
    override fun onCreate() {
        super.onCreate()
        // Создаем канал уведомлений (для Android 8.0 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = getNotificationChannel(
                id = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
                name = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startForegroundService()
        return START_STICKY_COMPATIBILITY
    }

    private fun createNotification(time: Long) : Notification {
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            value = "$time fafaewf",
            number = STOPWATCH_SERVICE_NOTIFICATION_ID
        )
        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
        return notification
    }
    private fun updateNotification(time: Long) {
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            value = time,
            number = STOPWATCH_SERVICE_NOTIFICATION_ID
        )

        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
    }
    private fun updateNotification(seconds: Long,milliseconds: Int) {
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            value = seconds,
            number = STOPWATCH_SERVICE_NOTIFICATION_ID
        )

        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
    }

    private fun startForegroundService() {
        val notification = createNotification(_seconds.value)

        startSecondsTimer{ time ->
            updateNotification(time = time)
        }
        startMillisecondsTimer{
        }
        ServiceCompat.startForeground(this,100, notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }
    private var duration: Duration = Duration.ZERO
    private var s_duration: Duration = Duration.ZERO
    private var ms_duration: Duration = Duration.ZERO

    private lateinit var timer: Timer

    private fun startStopwatch(onTick: (time: Long) -> Unit) {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
            onTick(_seconds.value)
        }
    }
    private fun startSecondsTimer(onTick: (seconds: Long) -> Unit) {
        timer = fixedRateTimer(initialDelay = 0, period = 1000L) {
            s_duration = s_duration.plus(1.seconds)
            updateSecondsUnit() // Обновляем только секунды
            onTick(_seconds.value) // Передаем только секунды
        }
    }
    private fun startMillisecondsTimer(onTick: (milliseconds: Int) -> Unit) {
        timer = fixedRateTimer(initialDelay = 0, period = 1L) {
            ms_duration = ms_duration.plus(1.milliseconds)
            updateMillisecondsUnit() // Обновляем только миллисекунды
            onTick(_milliseconds.value) // Передаем только миллисекунды
        }
    }

    private fun updateTimeUnits() {
        duration.toComponents { time, _ ->
            this@StopwatchService._seconds.value = time
        }
    }
    private fun updateStopwatchUnits() {
        s_duration.toComponents { time, _ ->
            this@StopwatchService._seconds.value = time
        }
        ms_duration.toComponents { _, time ->
            this@StopwatchService._milliseconds.value = time
        }
    }
    private fun updateSecondsUnit() {
        s_duration.toComponents { seconds, _ ->
            this@StopwatchService._seconds.value = seconds
        }
    }

    private fun updateMillisecondsUnit() {
        ms_duration.toComponents { _, millis ->
            this@StopwatchService._milliseconds.value = millis
        }
    }
    fun getSecondsFlow(): Flow<Long>{
        return _seconds
    }
    fun getMillisecondsFlow(): Flow<Int>{
        return _milliseconds
    }
    fun getSecondsValue(): Long{
        return _seconds.value
    }
    fun getMillisecondsValue(): Int{
        return _milliseconds.value
    }
    companion object {
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID = "STOPWATCH_CHANNEL"//STOPWATCH_CHANNEL
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME = "STOPWATCH"
        private const val STOPWATCH_SERVICE_NOTIFICATION_ID = 1
    }
}