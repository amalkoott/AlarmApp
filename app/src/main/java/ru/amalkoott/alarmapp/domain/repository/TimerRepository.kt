package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.TimeTemplate

interface TimerRepository {
    fun start(point: Long): Flow<Long>
    //fun start(point: TimeTemplate): Flow<TimeTemplate>
    fun pause()
    fun resume()
    fun finish()
    fun add(value: Long)
}