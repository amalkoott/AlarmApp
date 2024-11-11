package ru.amalkoott.alarmapp.domain.model

import java.sql.Time

class StopwatchRecord(
    //private val circle: Int,
    private val recordTime: Long,
    private val totalTime: Long
) {
    fun getRecordTime(): Long{
        return recordTime
    }
    fun getTotalTime(): Long{
        return totalTime
    }
}
