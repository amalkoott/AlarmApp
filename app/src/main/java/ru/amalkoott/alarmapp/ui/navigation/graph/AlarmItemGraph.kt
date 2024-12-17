package ru.amalkoott.alarmapp.ui.navigation.graph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import ru.amalkoott.alarmapp.ui.navigation.route.AppRoute
import ru.amalkoott.alarmapp.ui.navigation.route.AlarmItemRoute
import ru.amalkoott.alarmapp.ui.screen.AlarmItem
import ru.amalkoott.alarmapp.ui.screen.DatePickerScreen
import ru.amalkoott.alarmapp.ui.screen.MelodyPickerScreen
import ru.amalkoott.alarmapp.ui.screen.TimePickerScreen

fun NavGraphBuilder.alarmItemNavGraph(navController: NavController){
    navigation(
        route = AppRoute.Item.route,
        startDestination = AlarmItemRoute.Main.name,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(350)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(350)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(350)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(350))}

    ) {
        composable(route = AlarmItemRoute.Main.name){
            AlarmItem(navController)
        }
        dialog(route = AlarmItemRoute.Time.name){
            TimePickerScreen()
        }
        dialog(route = AlarmItemRoute.Date.name){
            DatePickerScreen()
        }
        dialog(route = AlarmItemRoute.Melody.name) {
            MelodyPickerScreen()
        }
    }
}