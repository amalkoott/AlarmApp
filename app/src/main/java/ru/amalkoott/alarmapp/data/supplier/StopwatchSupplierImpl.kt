package ru.amalkoott.alarmapp.data.supplier

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.data.service.StopwatchService
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Inject


class StopwatchSupplierImpl @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
): StopwatchSupplier {
    private var intentService: Intent = Intent(applicationContext, StopwatchService::class.java)
    private lateinit var service: StopwatchService
    private var isBound = false
    private val seconds: MutableStateFlow<Long> = StopwatchService._seconds
    private val milliseconds: MutableStateFlow<Long> = StopwatchService._milliseconds

    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as StopwatchService.StopwatchBinder
            this@StopwatchSupplierImpl.service = binder.getService()
            this@StopwatchSupplierImpl.isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun supply(): Pair<Flow<Long>,Flow<Long>> {
        applicationContext.bindService(intentService,serviceConnection,Context.BIND_AUTO_CREATE)
        Log.d("stopwatch","supply start()")

        ContextCompat.startForegroundService(applicationContext, intentService)

        return Pair(seconds,milliseconds)
    }

    override fun stopSupply() {
        if(isBound){
            service.stopStopwatch()
        }

        Log.d("stopwatch","supply stop()")
    }

    override fun restartSupply() {
        if(isBound){
            service.restartStopwatch()
        }
        Log.d("stopwatch","supply restart()")
    }

    override fun cancelSupply() {
        applicationContext.unbindService(serviceConnection)
        Log.d("stopwatch","supply cancel()")

        applicationContext.stopService(intentService)
    }

    override fun getCurrentTime(): TimeTemplate {
        return TimeTemplate(_seconds = seconds.value, _milliseconds = milliseconds.value)
    }

}