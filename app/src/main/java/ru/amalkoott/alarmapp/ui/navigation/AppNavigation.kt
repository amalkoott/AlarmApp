package ru.amalkoott.alarmapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.amalkoott.alarmapp.ui.component.AppBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { AppBottomBar(navController) }
    ){
        NavHost(navController = navController, startDestination = BottomRoute.Alarm.name){
            appNavGraph(navController)
        }
    }

}