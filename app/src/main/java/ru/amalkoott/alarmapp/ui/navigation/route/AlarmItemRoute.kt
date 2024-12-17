package ru.amalkoott.alarmapp.ui.navigation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AlarmItemRoute(
    val name: String,
    val route: String,
    val icon: ImageVector
) {
    object Main : AlarmItemRoute(
        name = "Main",
        route = "Main",
        icon = Icons.Filled.AccountBox
    )
    object Time : AppRoute(
        name = "Time",
        route = "Time",
        icon = Icons.Filled.Star
    )

    object Date : AppRoute(
        name = "Date",
        route = "Date",
        icon = Icons.Filled.Search
    )

    object Melody : AppRoute(
        name = "Melody",
        route = "Melody",
        icon = Icons.Filled.Email
    )
}