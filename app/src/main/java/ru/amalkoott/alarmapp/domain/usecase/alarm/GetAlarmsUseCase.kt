package ru.amalkoott.alarmapp.domain.usecase.alarm

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend fun expose(): Flow<List<Alarm>> {
        //repository.clearAll() //todo убрать потом
        return repository.getAlarms()
    }
}