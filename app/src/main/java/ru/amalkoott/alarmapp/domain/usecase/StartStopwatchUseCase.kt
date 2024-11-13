package ru.amalkoott.alarmapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.Time
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import javax.inject.Inject

class StartStopwatchUseCase @Inject constructor(
    private val stopwatchRepository: StopwatchRepository
) {
//    fun expose(): Flow<Time>{//Flow<Long> {
//        return stopwatchRepository.start()
//    }
    fun expose(): Time{//Flow<Long> {
        return stopwatchRepository.start()
    }
}