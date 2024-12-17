package ru.amalkoott.alarmapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.ui.navigation.route.AlarmItemRoute
import ru.amalkoott.alarmapp.ui.view.AlarmViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmScreen(
    navController: NavController,
    viewModel: AlarmViewModel = hiltViewModel()
){

    val onAddClick: () -> Unit = { navController.navigate(AlarmItemRoute.Main.name) }
    val alarms by viewModel.alarms.collectAsState()
    val selectedAlarm by remember { mutableStateOf(viewModel.selectedAlarm) }

    AlarmList(onAddClick,alarms)
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmList(
    click: () -> Unit,
    alarms: List<Alarm>
){
    Scaffold(
        topBar = {
            Text(
                text = "Будильники"
            )
        },
        bottomBar = {
            AlarmFAB(click)
        },
        modifier = Modifier
            .padding(vertical = 32.dp),
        content = {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(alarms){ alarm ->
                    Text(
                        text = alarm.name
                    )
                }
            }
        }
    )
}
@Composable
fun AlarmFAB(
    click: () -> Unit,
){
    FloatingActionButton(
        onClick = {
            click()
        },
        content = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add alarm"
            )
        }
    )
}



