package ru.amalkoott.alarmapp.domain.repository

import ru.amalkoott.alarmapp.domain.model.Alarm

interface AlarmManagerRepository {
    fun launch(alarm : Alarm)
    fun cancel()
}