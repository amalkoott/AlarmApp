package ru.amalkoott.alarmapp.domain.usecase.timer

import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class PauseUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(){
        repository.pause()
    }
}