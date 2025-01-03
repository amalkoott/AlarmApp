package ru.amalkoott.alarmapp.domain.usecase.alarm

import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmManagerRepository
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import javax.inject.Inject

class SwitchAlarmUseCase @Inject constructor(
    private val managerRepository: AlarmManagerRepository,
) {
    fun expose(alarm: Alarm){
        if(alarm.isActive) managerRepository.launch(alarm) else managerRepository.cancel()
    }
}