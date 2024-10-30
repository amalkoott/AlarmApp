package ru.amalkoott.alarmapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey
    val id: Long,
    val name: String
)
