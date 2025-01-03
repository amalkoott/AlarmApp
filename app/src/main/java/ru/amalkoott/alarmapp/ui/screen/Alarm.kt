package ru.amalkoott.alarmapp.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.amalkoott.alarmapp.R
import ru.amalkoott.alarmapp.ui.navigation.route.AppRoute
import ru.amalkoott.alarmapp.ui.view.AlarmViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlarmScreen(
    navController: NavController,
){
    val onAddClick: () -> Unit = { navController.navigate(AppRoute.Item.name) }
    Alarms(onAddClick)
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Alarms(
    click: () -> Unit,
    viewModel: AlarmViewModel = hiltViewModel()

){
    val alarms by viewModel.alrms.collectAsState()
    val selectedAlarm by remember { mutableStateOf(viewModel.selectedAlarm) }
    val context = LocalContext.current

    val alarm_label : String = ContextCompat.getString(context, R.string.alarms_label)
    Scaffold(
        topBar = {
            Text(
                text = alarm_label
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
                items(
                    items = alarms,
                    key = { alarm -> alarm.value.id!! }
                ){ alarm ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ){

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            Text(
                                text = "${alarm.value.id.toString()} ${alarm.value.time}"
                            )

                            Switch(
                                checked = alarm.value.isActive,
                                onCheckedChange = {
                                    viewModel.onSwitchAlarm(alarm)
                                }
                            )

                            Button(
                                onClick = {
                                    if (selectedAlarm.value === alarm.value) { viewModel.onSelectClick(null) }
                                    else { viewModel.onSelectClick(alarm.value) }
                                          },
                                modifier = Modifier
                                    .size(24.dp)
                            ) {

                            }
                        }
                        if (selectedAlarm.value != null && selectedAlarm.value!!.id === alarm.value.id){
                            Text(
                                text = alarm.value.time.toString() + alarm.value.description.toString()
                            )
                        }
                    }

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



