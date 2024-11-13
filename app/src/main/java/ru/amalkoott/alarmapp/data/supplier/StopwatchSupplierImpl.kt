package ru.amalkoott.alarmapp.data.supplier

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
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

    var serviceConnection: ServiceConnection
    init {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as StopwatchService.StopwatchBinder
                this@StopwatchSupplierImpl.service = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
            }
        }
        applicationContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun supply() {
        ContextCompat.startForegroundService(applicationContext, intent)
    }
    override fun getSeconds():Flow<Long>{
        return if (isBound) service?.getSecondsFlow() ?: flow { 0 } else flow { 0 }
    }
    override fun getMilliseconds():Flow<Int>{
        return if (isBound) service?.getMillisecondsFlow() ?: flow { 0 } else flow { 0 }
    }

    override fun stopTime() {
        if (isBound) {
            applicationContext.unbindService(serviceConnection)
            isBound = false
        }
        applicationContext.stopService(intent)
    }

    override fun resetTime() {
        if (isBound) { TODO("reset stopwatch") }
    }

    override fun getCurrentSeconds(): Long{
        var seconds: Long = 0
        if (isBound) {
            seconds = service?.getSecondsValue() ?: 0
        } else{
            throw IllegalArgumentException()
        }
        return seconds
    }
    override fun getCurrentMilliseconds(): Int{
        var milliseconds: Int = 0
        if (isBound) {
            milliseconds = service?.getMillisecondsValue() ?: 0
        } else{
            throw IllegalArgumentException()
        }
        return milliseconds
    }

    private var service: StopwatchService? = null
    private var isBound = false
}