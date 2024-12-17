package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    fun start(point: Long): Flow<Long>
    fun pause()
    fun resume()
    fun finish()
    fun add(value: Long)
}