package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord

interface StopwatchRepository {
    fun start(): Flow<Long>
    fun stop()
    fun reset()
    fun getRecords(): Flow<List<StopwatchRecord>>
    fun markRecord(): StopwatchRecord
}