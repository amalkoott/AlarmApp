package ru.amalkoott.alarmapp.ui.component

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.amalkoott.alarmapp.ui.navigation.BottomRoute

@Composable
fun AppBottomBar(navController: NavController){
    val routes = listOf(
        BottomRoute.Alarm,
        BottomRoute.Stopwatch,
        BottomRoute.Timer
    )

    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        routes.forEach{ screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = screen.name) },
                label = { Text(text = screen.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true,
                onClick = {
                    navController.navigate(screen.route){
                        //todo ("почекать еще, криво работает")
                        popUpTo(navController.graph.startDestinationId) {
                            // Убираем все экраны до начального
                            inclusive = true
                        }

                        // Не добавляем новый экран в стек, если он уже есть
                        launchSingleTop = false
                    }
                }
            )
        }
    }

}