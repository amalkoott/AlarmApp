package ru.amalkoott.alarmapp.ui.view.timer

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.amalkoott.alarmapp.ui.view.timer.strategy.TimerDisplayStrategy

class RunnedTimer : TimerDisplayStrategy {
    @Composable
    override fun draw(viewModel: TimerViewModel) {
        val state by viewModel.timerState.collectAsState()
        val timerOutput by viewModel.time.collectAsState()

        viewModel.outputDisplayStrategy(state).draw(timerOutput)
        Row{
            Button(
                onClick = {
                    viewModel.addOneMinute()
                }
            ){
                Text(
                    text = "1 min"
                )
            }
            Button(
                onClick = {
                    viewModel.addTenMinutes()
                }
            ){
                Text(
                    text = "10 min"
                )
            }
        }

        val activeButtonText by viewModel.activeButtonText.collectAsState()
        Button(
            onClick = {
                when(state){
                    TimerViewModel.TimerState.STARTED -> {
                        viewModel.pause()
                    }
                    TimerViewModel.TimerState.PAUSED -> {
                        viewModel.resume()
                    }
                    TimerViewModel.TimerState.RESUMED -> {
                        viewModel.pause()
                    }
                    else -> { }
                }
            }
        ){
            Text(
                text = activeButtonText
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