package ru.amalkoott.alarmapp.ui.screen

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.amalkoott.alarmapp.ui.navigation.route.AlarmItemRoute


@Composable
fun AlarmItemScreen(navController: NavHostController, onSaveClick: () -> Unit){
    val pickers = listOf(
        AlarmItemRoute.Time,
        AlarmItemRoute.Date,
        AlarmItemRoute.Melody
    )

    Box(
        Modifier.padding(horizontal = 16.dp).fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column {
            pickers.forEach{
                Button(
                    onClick = { navController.navigate(it.name) }
                ) {
                    Text(text = it.name)
                }
            }

            val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
            Button(
                onClick = {
                    backDispatcher?.onBackPressed()
                    onSaveClick()
                }
            ) {
                Text(text = "SAVE")
            }
        }
    }
}