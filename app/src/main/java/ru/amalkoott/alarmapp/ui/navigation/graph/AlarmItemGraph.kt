package ru.amalkoott.alarmapp.ui.navigation.graph

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import ru.amalkoott.alarmapp.ui.navigation.route.AlarmItemRoute
import ru.amalkoott.alarmapp.ui.navigation.route.AppRoute
import ru.amalkoott.alarmapp.ui.screen.AlarmItemScreen
import ru.amalkoott.alarmapp.ui.screen.DatePickerScreen
import ru.amalkoott.alarmapp.ui.screen.MelodyPickerScreen
import ru.amalkoott.alarmapp.ui.screen.TimePickerScreen
import ru.amalkoott.alarmapp.ui.view.CreateAlarmViewModel

@Composable
fun AlarmItemNavGraph(){
    val navController = rememberNavController()
    val viewModel: CreateAlarmViewModel = hiltViewModel()

    val onTimeConfirm: (Int,Int) -> Unit = { hour, minute -> viewModel.onTimeConfirm(hour,minute) }
    val onTimeDismiss: () -> Unit = { viewModel.onTimeDismiss() }

    val onSaveClick: () -> Unit = { viewModel.add() }

    NavHost(navController = navController, startDestination = AlarmItemRoute.Main.name){
        composable(route = AlarmItemRoute.Main.name){
            AlarmItemScreen(navController, onSaveClick)
        }
        dialog(route = AlarmItemRoute.Time.name){
            TimePickerScreen(
                onConfirm = onTimeConfirm,
                onDismiss = onTimeDismiss
            )
        }
        dialog(route = AlarmItemRoute.Date.name){
            DatePickerScreen()
        }
        dialog(route = AlarmItemRoute.Melody.name) {
            MelodyPickerScreen()
        }
    }
}
