package ru.amalkoott.alarmapp.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("Alarm","INTENT ${p1}")

        Toast.makeText(context, "Будильник сработал!", Toast.LENGTH_SHORT).show();
    }
}