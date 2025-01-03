package ru.amalkoott.alarmapp.ui.view.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.ui.view.timer.strategy.TimerDisplayStrategy

class EmptyTimer : TimerDisplayStrategy {
    @Composable
    override fun draw(viewModel: TimerViewModel) {
        val timerInput by remember { mutableStateOf(TimeTemplate(1,20,7)) }

        TimerInput(timerInput)
        val activeButtonText by viewModel.activeButtonText.collectAsState()

        Button(
            onClick = { viewModel.start(timerInput) }
        ){
            Text(
                text = "START"//activeButtonText
            )
        }
    }

    @Composable
    private fun TimerInput(
        startValue: TimeTemplate
    ){
        var hours by remember { mutableStateOf(startValue.hours) }
        var minuets by remember { mutableStateOf(startValue.minutes) }
        var seconds by remember { mutableStateOf(startValue.seconds) }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = hours.toString(),
                onValueChange = { hours = it.toLong()
                    startValue.hours = hours},
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = ":",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.5f))
            TextField(
                value = minuets.toString(),
                onValueChange = { minuets = it.toLong()
                    startValue.minutes = minuets},
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = ":",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.5f))
            TextField(
                value = seconds.toString(),
                onValueChange = { seconds = it.toLong()
                    startValue.seconds = seconds},
                modifier = Modifier
                    .weight(1f)
            )
        }

    }
}