package ru.amalkoott.alarmapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import ru.amalkoott.alarmapp.ui.screen.AlarmItem
import ru.amalkoott.alarmapp.ui.screen.DatePickerScreen
import ru.amalkoott.alarmapp.ui.screen.MelodyPickerScreen
import ru.amalkoott.alarmapp.ui.screen.TimePickerScreen

fun NavGraphBuilder.alarmItemNavGraph(navController: NavController){
    composable(route = DialogRoute.Main.name){
        AlarmItem(navController)
    }
    dialog(route = DialogRoute.Time.name){
        TimePickerScreen()
    }
    dialog(route = DialogRoute.Date.name){
        DatePickerScreen()
    }
    dialog(route = DialogRoute.Melody.name) {
        MelodyPickerScreen()
    }
}