package ru.amalkoott.alarmapp.data.repository

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.amalkoott.alarmapp.data.receiver.AlarmReceiver
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.repository.AlarmManagerRepository
import javax.inject.Inject

class AlarmManagerRepositoryImpl @Inject constructor(
    private val alarmManager: AlarmManager,
    private val context: Context
): AlarmManagerRepository {

    @SuppressLint("ScheduleExactAlarm")
    override fun launch(alarm: Alarm) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
          //  putExtra("EXTRA_MESSAGE", alarmItem.message)
        }
            // Toast.makeText(context,"WOW",Toast.LENGTH_SHORT).show()
        Log.d("Alarm","it's turn on\n${alarm.time}")


       // val alarmTime = alarmItem.alarmTime.atZone(ZoneId.systemDefault()).toEpochSecond()*1000L
        
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + alarm.time.toLong() * 1000,
            PendingIntent.getBroadcast(
                context,
                alarm.time.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

    }

    override fun cancel() {
        Log.d("Alarm","it's turn off")
    }
}