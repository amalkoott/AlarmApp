package ru.amalkoott.alarmapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import ru.amalkoott.alarmapp.data.AppDao
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val dao: AppDao
) : AlarmRepository{
    override fun getAlarms(): Flow<List<Alarm>> {
        return dao.all()
    }

    override suspend fun save(alarm: Alarm): Long = withContext(Dispatchers.IO) {
        return@withContext dao.save(alarm)
    }

    override suspend fun update(alarm: Alarm): Unit = withContext(Dispatchers.IO) {
        Log.d("",dao.update(alarm).toString())
    }

    override suspend fun delete(id: Long) {
        dao.delete(id)
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        dao.clearAll()
    }

    override suspend fun getById(id: Long): Alarm {
        return dao.byId(id)
    }
}