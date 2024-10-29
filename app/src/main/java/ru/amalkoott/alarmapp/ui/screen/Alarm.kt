package ru.amalkoott.alarmapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import ru.amalkoott.alarmapp.ui.navigation.DialogRoute

@Composable
fun AlarmScreen(
    toTimePicker: () -> Unit,
    toDatePicker: () -> Unit,
    toMelodyPicker: () -> Unit,
    navController: NavController
){
    Text("Alarm")

    val pickers = listOf(
        DialogRoute.Time,
        DialogRoute.Date,
        DialogRoute.Melody
    )

    Column {
        pickers.forEach{
            Button(
                onClick = { navController.navigate(it.name) }
            ) {
                Text(text = it.name)
            }
        }
    }
}