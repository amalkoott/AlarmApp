package ru.amalkoott.alarmapp.ui.screen

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.amalkoott.alarmapp.ui.navigation.DialogRoute
import ru.amalkoott.alarmapp.ui.view.CreateAlarmViewModel

@Composable
fun AlarmItem(
    navController: NavController,
   // onSaveClick:()-> Unit
    viewModel: CreateAlarmViewModel = hiltViewModel()
){
    val pickers = listOf(
        DialogRoute.Time,
        DialogRoute.Date,
        DialogRoute.Melody
    )

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
                viewModel.add()
                Log.d("ALARM","$navController")
            }
        ) {
            Text(text = "SAVE")
        }
    }
}