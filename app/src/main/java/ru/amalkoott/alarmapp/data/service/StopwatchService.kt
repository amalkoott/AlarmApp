package ru.amalkoott.alarmapp.data.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ServiceCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotification
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotificationChannel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


@AndroidEntryPoint
class StopwatchService : Service(){
    companion object {
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID = "STOPWATCH_CHANNEL"
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME = "STOPWATCH"
        private const val STOPWATCH_SERVICE_NOTIFICATION_ID = 1

        val _seconds = MutableStateFlow(0L)
        val _milliseconds = MutableStateFlow(0L)
    }
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val binder = StopwatchBinder()

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        val notificationChannel = getNotificationChannel(
            id = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            name = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        startForegroundService()
        return START_STICKY_COMPATIBILITY
    }

    private fun startForegroundService() {
        val notification = getNotification(
            applicationContext,
            STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            _seconds.value,
            STOPWATCH_SERVICE_NOTIFICATION_ID
        )

        notificationManager.notify(STOPWATCH_SERVICE_NOTIFICATION_ID, notification)

        startStopwatch{ seconds ->
            updateTime(seconds)
        }

        ServiceCompat.startForeground(this, STOPWATCH_SERVICE_NOTIFICATION_ID, notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }

    private var ms_duration: Duration = Duration.ZERO
    private lateinit var timer: Timer

    private fun startStopwatch(onTick: (seconds: Long) -> Unit) {
        timer = fixedRateTimer(initialDelay = 0, period = 10L) {
            ms_duration = ms_duration.plus(1.milliseconds)
            updateTimeUnits()
            onTick(_seconds.value)
        }
    }

    private fun updateTimeUnits() {
        ms_duration.toComponents { _, _, _, millis, nanos ->
            val milliseconds = (nanos / 1_000_000) % 100
            if(milliseconds == 99){
                _seconds.value += 1
            }
            _milliseconds.value = milliseconds.toLong()
        }
    }

    private fun updateTime(seconds: Long) {
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            seconds = seconds,
            number = STOPWATCH_SERVICE_NOTIFICATION_ID
        )

        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        notificationManager.cancel(STOPWATCH_SERVICE_NOTIFICATION_ID)
        stopStopwatch()
        resetStopwatch()

        super.onDestroy()
    }
    fun stopStopwatch(){
        timer.cancel()

    }
    private fun resetStopwatch(){
        _seconds.value =  0L
        _milliseconds.value = 0L
    }

    fun restartStopwatch(){
        startStopwatch{ seconds ->
            updateTime(seconds)
        }
    }
}