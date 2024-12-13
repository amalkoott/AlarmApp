package ru.amalkoott.alarmapp.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.usecase.ResetTimerUseCase
import ru.amalkoott.alarmapp.domain.usecase.StartTimerUseCase
import ru.amalkoott.alarmapp.domain.usecase.PauseTimerUseCase
import ru.amalkoott.alarmapp.domain.usecase.ResumeTimerUseCase
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resumeTimerUseCase: ResumeTimerUseCase,
    private val finishTimerUseCase: ResetTimerUseCase
): ViewModel() {
    private var _timerValue = MutableStateFlow(0L)
    val timerValue: StateFlow<Long> get()  = _timerValue

    fun start(point: Long){
        viewModelScope.launch {
            startTimerUseCase.expose(point).collect{
                _timerValue.value = it
            }
        }
    }
    fun pause(){
        pauseTimerUseCase.expose()
    }
    fun resume(){
        resumeTimerUseCase.expose()
    }
    fun finish(){
        finishTimerUseCase.expose()
    }
}