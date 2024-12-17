package ru.amalkoott.alarmapp.domain.usecase.timer

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class StartUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(point: Long): Flow<Long> {
        return repository.start(point)
    }
}