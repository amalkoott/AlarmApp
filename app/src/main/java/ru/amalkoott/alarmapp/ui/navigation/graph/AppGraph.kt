package ru.amalkoott.alarmapp.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.amalkoott.alarmapp.ui.navigation.route.AppRoute
import ru.amalkoott.alarmapp.ui.screen.AlarmScreen
import ru.amalkoott.alarmapp.ui.screen.StopwatchScreen
import ru.amalkoott.alarmapp.ui.view.timer.TimerScreen

@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppRoute.Alarm.name){
        composable(route = AppRoute.Alarm.name){
            AlarmScreen(navController)
        }
        composable(route = AppRoute.Stopwatch.name){
            StopwatchScreen()
        }
        composable(route = AppRoute.Timer.name){
            TimerScreen()
        }
        composable(route = AppRoute.Item.name){
            AlarmItemNavGraph()
        //AlarmItemGraph(navController)
        }
        //alarmItemNavGraph(navController)
    }
}