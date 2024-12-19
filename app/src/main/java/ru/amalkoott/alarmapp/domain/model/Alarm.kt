package ru.amalkoott.alarmapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    var description: String?,
    var time: Int,
    var isActive: Boolean = false,

){
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    companion object{
        val EMPTY_ALARM = Alarm("empty_alarm", 0)
    }


}
