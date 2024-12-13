package ru.amalkoott.alarmapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class StartTimerUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(point: Long): Flow<Long> {
        return repository.start(point)
    }
}