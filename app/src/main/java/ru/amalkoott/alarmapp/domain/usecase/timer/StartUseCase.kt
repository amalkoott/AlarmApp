package ru.amalkoott.alarmapp.domain.usecase.timer

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amalkoott.alarmapp.domain.model.ChronoTime
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import javax.inject.Inject

class StartUseCase @Inject constructor(
    private val repository: TimerRepository
) {
    fun expose(point: Long): Flow<Long> {
        return repository.start(point)
    }
    fun expose(point: TimeTemplate): Flow<Long> {
        val start = point.hours * 60 * 60 + point.minutes * 60 + point.seconds

        return repository.start(start)
    }

}