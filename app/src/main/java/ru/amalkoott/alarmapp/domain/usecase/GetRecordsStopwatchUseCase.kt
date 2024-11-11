package ru.amalkoott.alarmapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import javax.inject.Inject

class GetRecordsStopwatchUseCase @Inject constructor(
    private val stopwatchRepository: StopwatchRepository
) {
    fun expose(): Flow<List<StopwatchRecord>> {
        return stopwatchRepository.getRecords()
    }
}