package ru.amalkoott.alarmapp.domain

interface ForegroundCompanion {
    val CHANNEL_ID : String
    val CHANNEL_NAME : String
    val CHANNEL_DESCRIPTION : String

    val NOTIFICATION_ID : Int
    val NOTIFICATION_TITLE: String
    val NOTIFICATION_DETAILS: String
    val NOTIFICATION_ICON: Int

    val NOTIFICATION_PERMISSION_REQUEST: String get() = "Please"
    val NOTIFICATION_PERMISSION_TITLE: String get() = "Set permission"
}