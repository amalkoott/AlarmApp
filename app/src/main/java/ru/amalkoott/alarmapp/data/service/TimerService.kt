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
import ru.amalkoott.alarmapp.R
import ru.amalkoott.alarmapp.domain.ForegroundCompanion
import ru.amalkoott.alarmapp.domain.ForegroundService
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.createNotificationChannel
import ru.amalkoott.alarmapp.utils.ForegroundServiceTools.notify

class TimerService: Service(), ForegroundService {
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

    override val serviceCompanion = object : ForegroundCompanion{
        override val CHANNEL_ID get() = getString(R.string.timer_channel_id)
        override val CHANNEL_NAME get() = getString(R.string.timer_channel_name)
        override val CHANNEL_DESCRIPTION get() = getString(R.string.timer_channel_description)
        override val NOTIFICATION_ID get() = 1//todo getInt(R.string.timer_notification_id)
        override val NOTIFICATION_TITLE get() = getString(R.string.timer_notification_title)
        override val NOTIFICATION_DETAILS get() = getString(R.string.timer_notification_details)
        override val NOTIFICATION_ICON get() = 1//todo getInt(R.string.timer_notification_icon)
    }

    companion object {
        const val TIMER_START = "POINT"
        const val TIMER_START_HOURS = "POINT_HOURS"
        const val TIMER_START_MINUTES = "POINT_MINUTES"
        const val TIMER_START_SECONDS = "POINT_SECONDS"

        var current = MutableStateFlow<Long>(0)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager.createNotificationChannel(serviceCompanion)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extra = intent?.extras
        point = extra?.getLong(TIMER_START) ?: 0L

        startForegroundService()
        return START_STICKY
    }
    private fun startForegroundService() {
        startTimer{ value ->
            update(value)
        }


        val notification = ForegroundServiceTools.getNotification(serviceCompanion, applicationContext, current.value)
        ServiceCompat.startForeground(this, serviceCompanion.NOTIFICATION_ID, notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }
    private fun update(value: Any){
        notificationManager.notify(serviceCompanion,this, value)
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
                else  callback?.onTaskCompleted(getString(R.string.timer_task_finished))
            }
        }

        handler.postDelayed(timerRunnable!!, 0)
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