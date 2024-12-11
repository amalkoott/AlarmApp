package ru.amalkoott.alarmapp.domain.supplier

import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.TimeTemplate

interface StopwatchSupplier {
    fun supply(): Pair<Flow<Long>,Flow<Long>>
    fun stopSupply()
    fun cancelSupply()
    fun restartSupply()
    fun getCurrentTime(): TimeTemplate
}