package ru.amalkoott.alarmapp.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.amalkoott.alarmapp.domain.model.Alarm

@Composable
fun AlarmItemNavigation(){
    val navHostController = rememberNavController()


    Column(modifier = Modifier
        .fillMaxSize()) {
        NavHost(navController = navHostController, startDestination = DialogRoute.Main.name){
            alarmItemNavGraph(navHostController)
        }
    }
}