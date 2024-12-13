package ru.amalkoott.alarmapp.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.ChronoTime
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Inject


class StopwatchRepositoryImpl @Inject constructor(
    private val stopwatchSupplier: StopwatchSupplier
) : StopwatchRepository {
    private val _records = MutableStateFlow<List<StopwatchRecord>>(emptyList())
    private var chronoTime: ChronoTime? = null

    override fun start(): ChronoTime{
        if (chronoTime == null){
            val time = stopwatchSupplier.supply()
            chronoTime = ChronoTime(time.first,time.second)
            Log.d("SUPPLIER","repository start()")

        } else {
            stopwatchSupplier.restartSupply()
            Log.d("SUPPLIER","repository re-start()")
        }

        return chronoTime as ChronoTime
    }

    override fun pause() {
        stopwatchSupplier.stopSupply()
        Log.d("SUPPLIER","repository stop()")

    }
    override fun resume() {
        chronoTime = null
        _records.value = emptyList()

        stopwatchSupplier.cancelSupply()
        Log.d("SUPPLIER","repository reset()")

    }

    override fun cancel() {
        stopwatchSupplier.cancelSupply()
        Log.d("SUPPLIER","repository cancel()")
    }

    override fun getRecords(): Flow<List<StopwatchRecord>>  {
        return _records
    }
    override fun markRecord(): StopwatchRecord {
        val currentTime = stopwatchSupplier.getCurrentTime()
        val lastTotalTime = if (_records.value.size != 0) _records.value.last().getTotalTime() else TimeTemplate(0,0,0,0)

        val recordTime = currentTime.minus(lastTotalTime)
        val circle = _records.value.size + 1
        val record = StopwatchRecord(circle,recordTime,currentTime)

        _records.value += record
        return record
    }
}