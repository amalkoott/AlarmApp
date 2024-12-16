package ru.amalkoott.alarmapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.amalkoott.alarmapp.ui.screen.AlarmScreen
import ru.amalkoott.alarmapp.ui.screen.StopwatchScreen
import ru.amalkoott.alarmapp.ui.screen.TimerScreen

fun NavGraphBuilder.appNavGraph(navController: NavController){
    composable(route = BottomRoute.Alarm.name){
        AlarmScreen(navController)
    }
    composable(route = DialogRoute.Item.name){
        AlarmItemNavigation()
    }
    composable(route = BottomRoute.Stopwatch.name){
        StopwatchScreen()
    }
    composable(route = BottomRoute.Timer.name){
        TimerScreen()
    }
}