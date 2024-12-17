package ru.amalkoott.alarmapp.ui.navigation.route

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppRoute(
    val name: String,
    val route: String,
    val icon: ImageVector
) {
    object Alarm : AppRoute(
        name = "Alarm",
        route = "Alarm",
        icon = Icons.Filled.Star
    )
    object Item : AppRoute(
        name = "Item",
        route = "Item",
        icon = Icons.Filled.Star
    )

    object Timer : AppRoute(
        name = "Timer",
        route = "Timer",
        icon = Icons.Filled.Search
    )

    object Stopwatch : AppRoute(
        name = "Stopwatch",
        route = "Stopwatch",
        icon = Icons.Filled.Email
    )
}