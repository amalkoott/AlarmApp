package ru.amalkoott.alarmapp.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class StopwatchReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
       if (intent.action == "android.provider.Time.STOPWATCH_INC"){
           Log.i( "ReceiverApp" , "SMS Received" )
       }
    }


}
