package ru.amalkoott.alarmapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import ru.amalkoott.alarmapp.ui.screen.AlarmScreen
import ru.amalkoott.alarmapp.ui.screen.StopwatchScreen
import ru.amalkoott.alarmapp.ui.screen.TimerScreen

fun NavGraphBuilder.appNavGraph(navController: NavController){
    dialogNavGraph(navController)
    /*
    composable(route = BottomRoute.Alarm.name){
        //AlarmScreen()
        dialogNavGraph(navController)
        /*
        NavHost(navController = navController, startDestination = BottomRoute.Alarm.name){
            appNavGraph(navController)
        }
        */
    }
    */
    composable(route = BottomRoute.Stopwatch.name){
        StopwatchScreen()
    }
    composable(route = BottomRoute.Timer.name){
        TimerScreen()
    }
    /*
    dialog(route = DialogRoute.Time.name){

    }
    */
}