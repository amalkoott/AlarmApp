package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.ChronoTime

interface StopwatchRepository {
    fun start(): ChronoTime
    fun stop()
    fun reset()
    fun cancel()
    fun getRecords(): Flow<List<StopwatchRecord>>
    fun markRecord(): StopwatchRecord
}