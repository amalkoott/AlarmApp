package ru.amalkoott.alarmapp.domain.usecase.alarm

import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend fun expose(alarm: Alarm){
        repository.update(alarm)
    }
}