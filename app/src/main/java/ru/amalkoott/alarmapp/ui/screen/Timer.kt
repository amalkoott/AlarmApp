package ru.amalkoott.alarmapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.amalkoott.alarmapp.ui.view.TimerViewModel

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel()
){
    var test by remember { mutableLongStateOf(10L) }
    val timerValue by viewModel.timerValue.collectAsState()
    Text("Timer")

    // задержать для большей прибавки

    Column(
        modifier = Modifier
            .padding(32.dp)
    ) {
        TextField(
            value = test.toString(),
            onValueChange = { test = it.toLong() }
        )
        Text(
            text = timerValue.toString()
        )
        Button(
            onClick = {
                viewModel.start(test)
            }
        ){
            Text(
                text = "START"
            )
        }

        Button(
            onClick = {
                viewModel.pause()
            }
        ){
            Text(
                text = "STOP"
            )
        }
        Button(
            onClick = {
                viewModel.resume()
            }
        ){
            Text(
                text = "RESUME"
            )
        }

        Button(
            onClick = {
                viewModel.finish()
            }
        ){
            Text(
                text = "FINISH"
            )
        }
    }
}