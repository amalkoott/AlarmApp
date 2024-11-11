package ru.amalkoott.alarmapp.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Inject


class StopwatchRepositoryImpl @Inject constructor(
    private val stopwatchSupplier: StopwatchSupplier
) : StopwatchRepository {
    private val _records = MutableStateFlow<List<StopwatchRecord>>(emptyList())

    override fun start(): Flow<Long> {
        return stopwatchSupplier.getTime()
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
        val record = StopwatchRecord(0,stopwatchSupplier.getLapMark())

        _records.value += record
        return record
    }
}