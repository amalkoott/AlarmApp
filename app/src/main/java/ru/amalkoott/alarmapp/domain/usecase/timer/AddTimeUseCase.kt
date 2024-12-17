package ru.amalkoott.alarmapp.domain.usecase.timer

import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class AddTimeUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(value: Long){
        repository.add(value)
    }
}