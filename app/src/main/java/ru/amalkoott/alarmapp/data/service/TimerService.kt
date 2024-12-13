package ru.amalkoott.alarmapp.data.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow

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

    companion object {
        const val TIMER_START = "POINT"
        var current = MutableStateFlow<Long>(0)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val extra = intent?.extras
        point = extra?.getLong(TIMER_START) ?: 0L

        startTimer()
        return START_STICKY
    }
    private fun startTimer(){
        timerRunnable = object : Runnable {
            override fun run() {
                if (point > 0L){
                    current.value = point
                    point--
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
        startTimer()
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