package ru.amalkoott.alarmapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey
    val id: Long,
    val name: String
){
    companion object{
        val EMPTY_ALARM = Alarm(0L,"empty_alarm")
    }
}
