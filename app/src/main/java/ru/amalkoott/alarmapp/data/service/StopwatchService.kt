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
import androidx.core.app.ServiceCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotification
import ru.amalkoott.alarmapp.utils.NotificationHelper.getNotificationChannel
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


@AndroidEntryPoint
class StopwatchService : Service(){
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val binder = StopwatchBinder()
    private val _time = MutableStateFlow<Long>(666)
    val time: Flow<Long> get() = _time

    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder  // Возвращаем биндер для связи с сервисом
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
//        startStopwatch { time ->
//            createNotification(time = time)
//        }

        return START_STICKY_COMPATIBILITY
    }
    private fun createNotification(time: Long) : Notification {
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            value = "$time fafaewf",
            number = STOPWATCH_SERVICE_NOTIFICATION_ID
        )
        //STOPWATCH_SERVICE_NOTIFICATION_ID = notification.number
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

        //STOPWATCH_SERVICE_NOTIFICATION_ID = notification.number
        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
      //  return notification
    }

    private fun startForegroundService() {
        val notification = createNotification(_time.value)
        //STOPWATCH_SERVICE_NOTIFICATION_ID = notification.number
        startStopwatch { time ->
            updateNotification(time = time)
        //createNotification(time = time)
        }

        ServiceCompat.startForeground(
            /* service = */ this,
            /* id = */ 100, // Cannot be 0
            /* notification = */ notification,
            /* foregroundServiceType = */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )
    }
    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer
    private fun startStopwatch(onTick: (time: Long) -> Unit) {
       // currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
            onTick(_time.value)
        }
    }
    private fun updateTimeUnits() {
        duration.toComponents { time, _ ->
            this@StopwatchService._time.value = time
        }
    }
    //fun getTime(): Flow<Long> = _time
    fun getHelloMessage(): String {
        return "Hello from Bound Service!"
    }
    fun getCurrentMark(): Long{
        return _time.value
    }
    fun getTimeValue(): Flow<Long>{
        return _time
    }

    companion object {
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID = "STOPWATCH_CHANNEL"//STOPWATCH_CHANNEL
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME = "STOPWATCH"
        private const val STOPWATCH_SERVICE_NOTIFICATION_ID = 1
    }
}
/*
@AndroidEntryPoint
class StopwatchService : Service() {
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    //private lateinit var notification: Notification

    private val _time = MutableStateFlow<Long>(0)
    val time: MutableStateFlow<Long> get() = _time

    private val binder = StopwatchBinder()

    private var duration: Duration = Duration.ZERO
    private lateinit var timer: Timer
    var currentState = mutableStateOf(StopwatchState.Idle)
        private set

    override fun onBind(p0: Intent?): IBinder  = binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()

    }
    private fun createNotificationChannel(){
        val channel = getNotificationChannel(
            id = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            name = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME)
        notificationManager.createNotificationChannel(channel)

        /*
        val channelId = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID//"my_channel_id"
        val channelName = "My Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel description"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 200, 100, 200)
        }

        notificationManager.createNotificationChannel(channel)
        */
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        startStopwatch { time ->
            createNotification(time = time)
        }
        /*
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                //setStopButton()
                startForegroundService()
                startStopwatch { time ->
                    createNotification(time = time)
                }
            }
            StopwatchState.Stopped.name -> {
                stopStopwatch()
                //setResumeButton()
            }
            StopwatchState.Reset.name -> {
                stopStopwatch()
                cancelStopwatch()
                stopForegroundService()
            }
        }


         */

        return super.onStartCommand(intent, flags, startId)
    }
    private fun startForegroundService() {
        val notification = createNotification(_time.value)
        startForeground(STOPWATCH_SERVICE_NOTIFICATION_ID,notification)
    }
    private fun startStopwatch(onTick: (time: Long) -> Unit) {
        currentState.value = StopwatchState.Started
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            duration = duration.plus(1.seconds)
            updateTimeUnits()
            onTick(_time.value)
        }
    }
    private fun updateTimeUnits() {
        duration.toComponents { time, _ ->
            this@StopwatchService._time.value = time
        }
    }
    private fun createNotification(time: Long) : Notification{
        val notification = getNotification(
            context = this,
            channelName = STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID,
            value = time
        )
        STOPWATCH_SERVICE_NOTIFICATION_ID = notification.number
        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
        return notification
        /*
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            val notification = NotificationCompat.Builder(this, STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID)
                .setContentText("Set permissions")
                .setContentTitle("please")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(true) // an ongoing notification means can't dismiss by the user.
                .setOnlyAlertOnce(true)
                .build()

            return notification
        }
        val notification = NotificationCompat.Builder(this, STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentText("Update: $time")
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true) // an ongoing notification means can't dismiss by the user.
            .setOnlyAlertOnce(true)
            .build()
        STOPWATCH_SERVICE_NOTIFICATION_ID = notification.number
        notificationManager.notify(
            STOPWATCH_SERVICE_NOTIFICATION_ID, notification
        )
        return notification
        */
    }
    fun getTime(): Flow<Long> {
        return time
    }
    private fun stopForegroundService() {
        notificationManager.cancel(STOPWATCH_SERVICE_NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    private fun stopStopwatch() {
        if (this::timer.isInitialized) timer.cancel()
        currentState.value = StopwatchState.Stopped
    }
    private fun cancelStopwatch() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
    }
    override fun onDestroy() {
        stopStopwatch()
        super.onDestroy()



        //stopStopwatch()
       // stopForeground(STOP_FOREGROUND_REMOVE)
       // stopSelf()
    }

    companion object {
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_ID = "STOPWATCH_CHANNEL"//STOPWATCH_CHANNEL
        private const val STOPWATCH_SERVICE_NOTIFICATION_CHANNEL_NAME = "STOPWATCH"
        private var STOPWATCH_SERVICE_NOTIFICATION_ID by Delegates.notNull<Int>()
    }

    enum class StopwatchState {
        Idle, Started, Stopped, Reset
    }
    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }
}
*/