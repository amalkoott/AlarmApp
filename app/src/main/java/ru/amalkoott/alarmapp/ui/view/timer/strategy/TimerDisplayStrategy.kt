package ru.amalkoott.alarmapp.ui.view.timer.strategy

import androidx.compose.runtime.Composable
import ru.amalkoott.alarmapp.ui.view.timer.TimerViewModel

interface TimerDisplayStrategy {
    @Composable
    fun draw(
        viewModel: TimerViewModel
    )
}