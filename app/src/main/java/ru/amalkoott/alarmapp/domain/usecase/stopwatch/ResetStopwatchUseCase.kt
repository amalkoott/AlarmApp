package ru.amalkoott.alarmapp.domain.usecase.stopwatch

import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import javax.inject.Inject

class ResetStopwatchUseCase @Inject constructor(
    private val stopwatchRepository: StopwatchRepository
) {
    fun expose(){
        stopwatchRepository.resume()
    }
}