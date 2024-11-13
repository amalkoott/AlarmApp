package ru.amalkoott.alarmapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.Time
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Inject


class StopwatchRepositoryImpl @Inject constructor(
    private val stopwatchSupplier: StopwatchSupplier
) : StopwatchRepository {
    private val _records = MutableStateFlow<List<StopwatchRecord>>(emptyList())

    override fun start(): Time{
        stopwatchSupplier.supply()
        val time = Time(stopwatchSupplier.getSeconds(),stopwatchSupplier.getMilliseconds())
        return time
    }

    override fun stop() {
        stopwatchSupplier.stopTime()
    }

    override fun reset() {
        stopwatchSupplier.resetTime()
    }

    override fun getRecords(): Flow<List<StopwatchRecord>>  {
        return _records
    }
    override fun markRecord(): StopwatchRecord {
        val record = StopwatchRecord(0,stopwatchSupplier.getCurrentSeconds())

        _records.value += record
        return record
    }
}