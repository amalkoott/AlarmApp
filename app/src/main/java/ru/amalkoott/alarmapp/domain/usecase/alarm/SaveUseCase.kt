package ru.amalkoott.alarmapp.domain.usecase.alarm

import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class SaveUseCase @Inject constructor(
    private val repository: AlarmRepository
) {
    suspend fun expose(alarm: Alarm){
        repository.save(alarm)
    }
}