package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.Time

interface StopwatchRepository {
    fun start(): Time
    fun stop()
    fun reset()
    fun getRecords(): Flow<List<StopwatchRecord>>
    fun markRecord(): StopwatchRecord
}