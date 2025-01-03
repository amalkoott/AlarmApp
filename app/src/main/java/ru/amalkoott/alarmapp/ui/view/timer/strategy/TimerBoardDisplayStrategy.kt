package ru.amalkoott.alarmapp.ui.view.timer.strategy

import androidx.compose.runtime.Composable
import ru.amalkoott.alarmapp.domain.model.TimeTemplate

interface TimerBoardDisplayStrategy {
    @Composable
    fun draw(timerValue: TimeTemplate)
}