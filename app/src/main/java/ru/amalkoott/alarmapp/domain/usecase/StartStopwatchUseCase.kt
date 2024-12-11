package ru.amalkoott.alarmapp.domain.usecase

import ru.amalkoott.alarmapp.domain.model.ChronoTime
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import javax.inject.Inject

class StartStopwatchUseCase @Inject constructor(
    private val stopwatchRepository: StopwatchRepository
) {
//    fun expose(): Flow<Time>{//Flow<Long> {
//        return stopwatchRepository.start()
//    }
    fun expose(): ChronoTime{//Flow<Long> {
        return stopwatchRepository.start()
    }
}