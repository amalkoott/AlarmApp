package ru.amalkoott.alarmapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.amalkoott.alarmapp.ui.view.StopwatchViewModel

/*
    что умеет:
    - запускать секундомер
    - стопить секундомер
    - збрасывать секундомер
    - отмечать время
     */

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun StopwatchScreen(
    viewModel: StopwatchViewModel = hiltViewModel()
){
    val isStarted by viewModel.isStarted.collectAsState()
    //val time by viewModel.temp_time.collectAsState()

    val seconds by viewModel.time.getSeconds().collectAsState()
    val milliseconds by viewModel.time.getMilliseconds().collectAsState()
    val records by viewModel.records.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)) {
        if (isStarted){
            Text("Таймер запущен!")

        }else{
            Text("Таймер остановлен!")

        }
        Row(){
            Text("sec: ${seconds}")
            Text("mls: ${milliseconds}")
        }
        StopwatchDisplay()
        StopwatchButton(
            icon = { if (isStarted) Icon(
                imageVector = Icons.Default.Star,
                contentDescription =  "")
            else Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription =  "")
            },
            onAction = { if (isStarted) viewModel.stop() else viewModel.start() }
        )

        Button(
            onClick = { viewModel.record() }
        ) {
            Text(text = "MARK!")
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()//.weight(1f)
        ) {
            items(records){ record ->
                Text(
                    text = record.getTotalTime().toString(),
                    modifier = Modifier
                )
            }
        }
    }


}
@Composable
fun StopwatchDisplay(){

}

@Composable
fun StopwatchButton(
    icon: @Composable () -> Unit,
    onAction: () -> Unit
){
    IconButton(
        onClick = { onAction() },
    ){
        icon()
    }
}

@Composable
fun StopwatchRemoveButton(){

}

@Composable
fun StopwatchActionButton(){

}

@Composable
fun StopwatchRecordButton(){

}