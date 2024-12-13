package ru.amalkoott.alarmapp.domain.usecase

import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class PauseTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(){
        repository.pause()
    }
}