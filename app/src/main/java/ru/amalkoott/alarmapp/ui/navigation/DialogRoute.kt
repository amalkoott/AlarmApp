package ru.amalkoott.alarmapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector


sealed class DialogRoute(
    val name: String,
    val route: String,
    val icon: ImageVector
) {
    object Time : BottomRoute(
        name = "Time",
        route = "Time",
        icon = Icons.Filled.Star
    )

    object Date : BottomRoute(
        name = "Date",
        route = "Date",
        icon = Icons.Filled.Search
    )

    object Melody : BottomRoute(
        name = "Melody",
        route = "Melody",
        icon = Icons.Filled.Email
    )
}
