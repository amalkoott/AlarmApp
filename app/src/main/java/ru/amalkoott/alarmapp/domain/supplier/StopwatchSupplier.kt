package ru.amalkoott.alarmapp.domain.supplier

import kotlinx.coroutines.flow.Flow

interface StopwatchSupplier {
    fun supply()
    fun getSeconds(): Flow<Long>//Flow<Long>
    fun getMilliseconds(): Flow<Int>//Flow<Long>
    fun stopTime()
    fun resetTime()
    fun getCurrentSeconds(): Long//StopwatchRecord
    fun getCurrentMilliseconds(): Int//StopwatchRecord
}