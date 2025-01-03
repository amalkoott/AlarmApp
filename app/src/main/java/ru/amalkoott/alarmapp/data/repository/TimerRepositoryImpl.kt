package ru.amalkoott.alarmapp.data.repository

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amalkoott.alarmapp.data.service.TimerService
import ru.amalkoott.alarmapp.data.service.TimerService.Companion.TIMER_START
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
): TimerRepository, TimerService.Callback {

    private lateinit var service: TimerService
    private var isBound = false
    private val intent = Intent(applicationContext,TimerService::class.java)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder?) {
            val binder = service as TimerService.TimerBinder
            this@TimerRepositoryImpl.service = binder.getService()
            this@TimerRepositoryImpl.service.setCallback(this@TimerRepositoryImpl)
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }
    }

    override fun onTaskCompleted(result: String) {
        finish()
    }

    override fun start(point: Long): Flow<Long> {
        intent.putExtra(TIMER_START,point)
        applicationContext.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
        ContextCompat.startForegroundService(applicationContext,intent)
        //applicationContext.startService(intent)

        return TimerService.current
    }

    /*
    override fun start(point: TimeTemplate): Flow<TimeTemplate> {
        val start = point.hours + point.minutes * 60 + point.seconds * 60 * 60
        intent.putExtra(TIMER_START,start)
        applicationContext.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
        ContextCompat.startForegroundService(applicationContext,intent)
        //applicationContext.startService(intent)

        return TimerService.current
    }
    */

    override fun pause() {
        if(isBound){
            service.pauseTimer()
        }
    }

    override fun resume() {
        if (isBound)
        {
            service.resumeTimer()
        }
    }

    override fun finish() {
        applicationContext.unbindService(serviceConnection)
        Toast.makeText(applicationContext,"FINISH!!!",Toast.LENGTH_SHORT).show()
    }

    override fun add(value: Long) {
        if(isBound){
            service.addTime(value)
        }
    }
}