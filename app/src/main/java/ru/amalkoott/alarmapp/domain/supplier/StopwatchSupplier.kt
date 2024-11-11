package ru.amalkoott.alarmapp.domain.supplier

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord

interface StopwatchSupplier {
    fun getTime(): Flow<Long>
    fun stopTime()
    fun resetTime()
    fun getLapMark(): Long//StopwatchRecord
}