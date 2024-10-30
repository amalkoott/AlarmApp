package ru.amalkoott.alarmapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.amalkoott.alarmapp.ui.navigation.DialogRoute
import ru.amalkoott.alarmapp.ui.view.AlarmViewModel

@Composable
fun AlarmScreen(
    toTimePicker: () -> Unit,
    toDatePicker: () -> Unit,
    toMelodyPicker: () -> Unit,
    navController: NavController,
    viewModel: AlarmViewModel = hiltViewModel()
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