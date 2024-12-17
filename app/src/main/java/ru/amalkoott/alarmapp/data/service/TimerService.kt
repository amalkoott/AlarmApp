package ru.amalkoott.alarmapp.data.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.ForegroundCompanion
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.createNotificationChannel
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.notify

class TimerService: Service() {
    private val handler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null
    private var point = 0L

    private val binder = TimerBinder()
    private var callback: Callback? = null

    interface Callback {
        fun onTaskCompleted(result: String)
    }
    fun setCallback(callback: Callback) {
        this.callback = callback
    }
    inner class TimerBinder: Binder() {
        fun getService(): TimerService{
            return this@TimerService
        }
    }

    companion object : ForegroundCompanion {
        const val TIMER_START = "POINT"

        var current = MutableStateFlow<Long>(0)

        override val CHANNEL_ID: String
            get() = "TIMER_CHANNEL_ID"
        override val CHANNEL_NAME: String
            get() = "TIMER_CHANNEL"
        override val NOTIFICATION_ID: Int
            get() = 1
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager.createNotificationChannel(Companion)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extra = intent?.extras
        point = extra?.getLong(TIMER_START) ?: 0L

        startForegroundService()//startTimer()
        return START_STICKY
    }
    private fun startForegroundService() {
        startTimer{ value ->
            update(value)
        }


        val notification = ForegroundServiceTools.getNotification(Companion, applicationContext, current.value)
        ServiceCompat.startForeground(this, NOTIFICATION_ID, notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }
    private fun update(value: Any){
        notificationManager.notify(Companion,this, value)
    }

    private fun startTimer(onTick: (current: String) -> Unit){
        timerRunnable = object : Runnable {
            override fun run() {
                if (point > 0L){
                    current.value = point
                    point--
                    onTick(current.value.toString())
                    handler.postDelayed(this, 1000)
                }
                else  callback?.onTaskCompleted("Task Finished")
            }
        }

        handler.postDelayed(timerRunnable!!, 0)  // Первая задержка 1 секунда
    }

    fun pauseTimer(){
        timerRunnable?.let { handler.removeCallbacks(it) }
    }
    fun resumeTimer(){
        startTimer{ value ->
            update(value)
        }
    }
    fun addTime(value: Long){
        CoroutineScope(Dispatchers.Default).launch{
            point += value
            current.value = point
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        current.value = 0L
        stopSelf()
        return super.onUnbind(intent)
    }
    override fun onDestroy() {
        super.onDestroy()
        timerRunnable?.let { handler.removeCallbacks(it) }
    }
}