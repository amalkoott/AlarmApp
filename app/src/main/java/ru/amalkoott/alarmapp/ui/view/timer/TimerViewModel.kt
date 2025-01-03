package ru.amalkoott.alarmapp.ui.view.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.model.TimeTemplate
import ru.amalkoott.alarmapp.domain.usecase.timer.AddTimeUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.FinishUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.PauseUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.ResumeUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.StartUseCase
import ru.amalkoott.alarmapp.ui.view.timer.strategy.TimerBoardDisplayStrategy
import ru.amalkoott.alarmapp.ui.view.timer.strategy.TimerDisplayStrategy
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val startUseCase: StartUseCase,
    private val pauseUseCase: PauseUseCase,
    private val resumeUseCase: ResumeUseCase,
    private val finishUseCase: FinishUseCase,
    private val addTimeUseCase: AddTimeUseCase
): ViewModel() {
    enum class TimerState{
        STARTED,
        STOPPED,
        PAUSED,
        RESUMED
    }
    private var _timerState = MutableStateFlow(TimerState.STOPPED)
    val timerState: StateFlow<TimerState> get()  = _timerState

    private val _time = MutableStateFlow(TimeTemplate())
    val time: StateFlow<TimeTemplate> get() = _time

    private val _activeButtonText = MutableStateFlow("START")
    val activeButtonText: StateFlow<String> get() = _activeButtonText

    fun start(point: TimeTemplate){
        viewModelScope.launch {
            startUseCase.expose(point).collect{
                _time.value = TimeTemplate.fromSeconds(it)
                Log.d("AlarmApp", it.toString())
            }
        }
        _timerState.value = TimerState.STARTED
        _activeButtonText.value = "PAUSE"
    }
    fun pause(){
        pauseUseCase.expose()
        _timerState.value = TimerState.PAUSED
        _activeButtonText.value = "RESUMED"
    }
    fun resume(){
        resumeUseCase.expose()
        _timerState.value = TimerState.RESUMED
        _activeButtonText.value = "PAUSE"
    }
    fun finish(){
        finishUseCase.expose()
        _timerState.value = TimerState.STOPPED
        _activeButtonText.value = "START"
    }
    fun addOneMinute(){
        addTimeUseCase.expose(60L)
    }
    fun addTenMinutes(){
        addTimeUseCase.expose(10 * 60L)
    }
    fun screenDisplayStrategy(state: TimerState): TimerDisplayStrategy {
        return when(state){
           TimerState.STOPPED -> EmptyTimer()
            else -> RunnedTimer()
        }
    }
    fun outputDisplayStrategy(state: TimerState) : TimerBoardDisplayStrategy {
        return if(state == TimerState.PAUSED) PausedTimerBoard() else RunnedTimerBoard()
    }
}