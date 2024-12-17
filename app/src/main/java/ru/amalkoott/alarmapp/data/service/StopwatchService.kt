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
import ru.amalkoott.alarmapp.domain.ForegroundCompanion
import ru.amalkoott.alarmapp.domain.ForegroundService
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.createNotificationChannel
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.notify
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


@AndroidEntryPoint
class StopwatchService : Service(), ForegroundService {
    companion object : ForegroundCompanion{
        val seconds = MutableStateFlow(0L)
        val milliseconds = MutableStateFlow(0L)

        override val CHANNEL_ID: String
            get() = "STOPWATCH_CHANNEL"

        override val CHANNEL_NAME: String
            get() = "STOPWATCH"

        override val NOTIFICATION_ID: Int
            get() = 1
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
        notificationManager.createNotificationChannel(Companion)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        startForegroundService()
        return START_STICKY_COMPATIBILITY
    }

    private fun startForegroundService() {
        //val notification = notificationManager.notify(Companion,applicationContext, _seconds.value)

        startStopwatch{ seconds ->
            updateTime(seconds)
        }

        val notification = ForegroundServiceTools.getNotification(Companion, applicationContext, seconds.value)

        ServiceCompat.startForeground(this, NOTIFICATION_ID, notification,
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
            onTick(seconds.value)
        }
    }

    private fun updateTimeUnits() {
        ms_duration.toComponents { _, _, _, millis, nanos ->
            val _milliseconds = (nanos / 1_000_000) % 100
            if(_milliseconds == 99){
                seconds.value += 1
            }
            milliseconds.value = _milliseconds.toLong()
        }
    }

    private fun updateTime(seconds: Long) {
        notificationManager.notify(Companion,this,seconds)
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopStopwatch()
        resetStopwatch()

        super.onDestroy()
    }
    fun stopStopwatch(){
        timer.cancel()

    }
    private fun resetStopwatch(){
        seconds.value =  0L
        milliseconds.value = 0L
    }

    fun restartStopwatch(){
        startStopwatch{ seconds ->
            updateTime(seconds)
        }
    }
}