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
import ru.amalkoott.alarmapp.ui.navigation.route.AppRoute

@Composable
fun AppBottomBar(navController: NavController){
    val routes = listOf(
        AppRoute.Alarm,
        AppRoute.Stopwatch,
        AppRoute.Timer
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = routes.any { it.route == currentDestination?.route }

    if(bottomBarDestination){
        BottomNavigation{
            routes.forEach{ screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = screen.name) },
                    label = { Text(text = screen.name) },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true,
                    onClick = {
                        navController.navigate(screen.route){
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }

                            launchSingleTop = false
                        }
                    }
                )
            }
        }
    }
}