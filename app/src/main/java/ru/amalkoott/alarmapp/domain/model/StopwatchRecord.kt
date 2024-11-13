package ru.amalkoott.alarmapp.domain.model

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
