package ru.amalkoott.alarmapp.ui.view.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.ui.view.timer.strategy.TimerBoardDisplayStrategy

class PausedTimerBoard : TimerBoardDisplayStrategy {
    @Composable
    override fun draw(timerValue: TimeTemplate) {
        PausedTimerOutput(timerValue)
    }

    @Composable
    fun PausedTimerOutput(timerValue: TimeTemplate){

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(
                text = timerValue.hours.toString(),
                modifier = Modifier
                    .weight(1f))
            Text(
                text = ":",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.5f))
            Text(
                text = timerValue.minutes.toString(),
                modifier = Modifier
                    .weight(1f))
            Text(
                text = ":",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(0.5f))
            Text(
                text = timerValue.seconds.toString(),
                modifier = Modifier
                    .weight(1f))
        }
    }
}