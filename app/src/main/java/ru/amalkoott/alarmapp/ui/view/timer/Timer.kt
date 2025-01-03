package ru.amalkoott.alarmapp.ui.view.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel()
){
    val state by viewModel.timerState.collectAsState()

    Text("Timer")
    
    Column(
        modifier = Modifier
            .padding(32.dp)
    ) {
       viewModel.screenDisplayStrategy(state).draw(viewModel)
    }
}