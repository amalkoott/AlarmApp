package ru.amalkoott.alarmapp.data.supplier

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amalkoott.alarmapp.data.service.StopwatchService
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Inject


class StopwatchSupplierImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
): StopwatchSupplier {
    private var intent: Intent = Intent(applicationContext, StopwatchService::class.java)

    init {
        //applicationContext.startService(intent)
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                // Получаем экземпляр сервиса через LocalBinder
                val binder = service as StopwatchService.StopwatchBinder
                myBoundService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
            }
        }
        applicationContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun getTime(): Flow<Long> {
        /*
        if (isBound) {
            val message = myBoundService?.getTime() ?: "Service not bound"
            //Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
        */
        ContextCompat.startForegroundService(applicationContext, intent)
        //myBoundService?.startForeground()
        return if (isBound) myBoundService?.getTimeValue() ?: flow { 0 } else flow { 0 }
    }

    override fun stopTime() {
        if (isBound) {
            val message = myBoundService?.getHelloMessage() ?: "Service not bound"
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
        /*
        if (isBound) {
            applicationContext.unbindService(serviceConnection)
            isBound = false
        }
        */
    }

    override fun resetTime() {
        if (isBound) {
            val message = myBoundService?.getHelloMessage() ?: "Service not bound"
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLapMark(): Long{//StopwatchRecord {
        val currentTime: Long
        if (isBound) {
            currentTime = myBoundService?.getCurrentMark() ?: 0//.getHelloMessage() ?: "Service not bound"
           // Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        } else{
            throw IllegalArgumentException()
        }
        return currentTime//StopwatchRecord(0,currentTime)
    }

    private var myBoundService: StopwatchService? = null
    private var isBound = false

    // Объект для связи с сервисом



    /*
    private val serviceIntent = Intent(applicationContext, StopwatchService::class.java)

    private var stopwatchService: StopwatchService? = null
    private var serviceBound = false

    override fun getTime(): Flow<Long> {
        serviceIntent.apply {
            putExtra(STOPWATCH_STATE,StopwatchService.StopwatchState.Started.name)
        }

        ContextCompat.startForegroundService(applicationContext, serviceIntent)

        return flow { stopwatchService?.getTime() }
    }

    override fun stopTime() {
        serviceIntent.apply {
            putExtra(STOPWATCH_STATE,StopwatchService.StopwatchState.Stopped.name)
        }
        ContextCompat.startForegroundService(applicationContext, serviceIntent)
        //applicationContext.stopService(serviceIntent)
    }

    override fun resetTime() {
        serviceIntent.apply {
            putExtra(STOPWATCH_STATE,StopwatchService.StopwatchState.Reset.name)
        }
        applicationContext.stopService(serviceIntent)
    }

    override fun getLapMark(): StopwatchRecord {
        serviceIntent.apply {
         //   putExtra(STOPWATCH_STATE,StopwatchService.StopwatchState.Lap.name)
        }
        TODO()
    }
    */

    /*
    private val serviceConnection = object : android.content.ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val stopwatchBinder = binder as StopwatchService.StopwatchBinder
            stopwatchService = stopwatchBinder.getService()
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            stopwatchService = null
            serviceBound = false
        }
    }
    */

}