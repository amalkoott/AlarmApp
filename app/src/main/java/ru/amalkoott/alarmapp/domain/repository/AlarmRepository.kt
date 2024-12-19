package ru.amalkoott.alarmapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.Alarm

interface AlarmRepository {
    fun getAlarms(): Flow<List<Alarm>>
    suspend fun save(alarm: Alarm): Long
    suspend fun update(alarm: Alarm)
    suspend fun delete(alarm: Alarm)
    suspend fun clearAll()
    suspend fun getById(id: Long): Alarm
}