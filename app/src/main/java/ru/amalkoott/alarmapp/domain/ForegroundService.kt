package ru.amalkoott.alarmapp.domain

interface ForegroundService {
    companion object : ForegroundCompanion {
        override val CHANNEL_ID: String
            get() = "CHANNEL_ID"
        override val CHANNEL_NAME: String
            get() = "CHANNEL_NAME"
        override val NOTIFICATION_ID: Int
            get() = 0
    }
}