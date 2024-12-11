package ru.amalkoott.alarmapp.domain.model

class TimeTemplate(
    private var _hours: Long = 0,
    private var _minutes: Long = 0,
    private var _seconds: Long = 0,
    private var _milliseconds: Long = 0
) {
    var milliseconds: Long = _milliseconds

    var seconds: Long = _seconds

    var minutes: Long = _minutes

    var hours: Long = _hours

    companion object {
        fun fromMilliseconds(value: Long): TimeTemplate{
            val milliseconds = value % 1000
            val seconds = (value / 1000 ) % 60
            val minutes = (value / 1000 / 60 ) % 60
            val hours = (value / 1000 / 60 / 60 ) % 60

            return TimeTemplate(_hours = hours, _minutes = minutes, _seconds = seconds, _milliseconds = milliseconds)
        }
    }

    fun inMilliseconds(): Long{
        val value = 1000 * (this.hours * 60 * 60 + this.minutes * 60 + this.seconds) + this.milliseconds
        return value
    }
    fun minus(value: TimeTemplate): TimeTemplate{
        return TimeTemplate.fromMilliseconds(this.inMilliseconds() - value.inMilliseconds())
    }
}