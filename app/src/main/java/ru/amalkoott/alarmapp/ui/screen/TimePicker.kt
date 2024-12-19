package ru.amalkoott.alarmapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen(
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
){
    Text("TIME PICKER")
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePicker(
            state = timePickerState
        )
        Button(onClick = {
            Log.d("ALARM",timePickerState.toString())
            onConfirm(timePickerState.hour, timePickerState.minute)
        }) {
            Text("Confirm selection")
        }
        Button(onClick = {
            onDismiss()
        }) {
            Text("Dismiss picker")
        }

    }
}