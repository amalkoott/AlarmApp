package ru.amalkoott.alarmapp.domain.model

class StopwatchRecord(
    private val circle: Int,
    private val recordTime: TimeTemplate,
    private val totalTime: TimeTemplate
) {
    fun getRecordTime(): TimeTemplate{
        return recordTime
    }
    fun getTotalTime(): TimeTemplate{
        return totalTime
    }
    fun getTimeString(): String{
        return "${recordTime.hours}:${recordTime.minutes}:${recordTime.seconds},${recordTime.milliseconds}"
    }

    override fun toString(): String {
        super.toString()

        return "$circle\t${recordTime.hours}:${recordTime.minutes}:${recordTime.seconds},${recordTime.milliseconds}\t   \t${totalTime.hours}:${totalTime.minutes}:${totalTime.seconds},${totalTime.milliseconds}"
    }
}
