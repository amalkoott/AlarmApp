package ru.amalkoott.alarmapp.ui.navigation

import androidx.compose.material3.TimePicker
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import ru.amalkoott.alarmapp.ui.screen.AlarmScreen
import ru.amalkoott.alarmapp.ui.screen.DatePickerScreen
import ru.amalkoott.alarmapp.ui.screen.MelodyPickerScreen
import ru.amalkoott.alarmapp.ui.screen.TimePickerScreen

fun NavGraphBuilder.dialogNavGraph(navController: NavController) {
    composable(route = BottomRoute.Alarm.name){
        AlarmScreen(
            toTimePicker = { navController.navigate(DialogRoute.Time.name) },
            toDatePicker = { navController.navigate(DialogRoute.Date.name) },
            toMelodyPicker = { navController.navigate(DialogRoute.Melody.name) },
            navController = navController
        )
        /*
        NavHost(navController = navController, startDestination = BottomRoute.Alarm.name){
            appNavGraph(navController)
        }
        */
    }
    dialog(route = DialogRoute.Time.name){
        TimePickerScreen()
    }
    dialog(route = DialogRoute.Date.name){
        DatePickerScreen()
    }
    dialog(route = DialogRoute.Melody.name){
        MelodyPickerScreen()
    }
}