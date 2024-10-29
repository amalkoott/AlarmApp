package ru.amalkoott.alarmapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomRoute(
    val name: String,
    val route: String,
    val icon: ImageVector
) {
    object Alarm : BottomRoute(
        name = "Alarm",
        route = "Alarm",
        icon = Icons.Filled.Star
    )

    object Timer : BottomRoute(
        name = "Timer",
        route = "Timer",
        icon = Icons.Filled.Search
    )

    object Stopwatch : BottomRoute(
        name = "Stopwatch",
        route = "Stopwatch",
        icon = Icons.Filled.Email
    )
}
