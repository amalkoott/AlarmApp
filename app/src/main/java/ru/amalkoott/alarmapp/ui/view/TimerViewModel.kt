package ru.amalkoott.alarmapp.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.usecase.timer.AddTimeUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.FinishUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.StartUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.PauseUseCase
import ru.amalkoott.alarmapp.domain.usecase.timer.ResumeUseCase
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val startUseCase: StartUseCase,
    private val pauseUseCase: PauseUseCase,
    private val resumeUseCase: ResumeUseCase,
    private val finishUseCase: FinishUseCase,
    private val addTimeUseCase: AddTimeUseCase
): ViewModel() {
    private var _timerValue = MutableStateFlow(0L)
    val timerValue: StateFlow<Long> get()  = _timerValue

    fun start(point: Long){
        viewModelScope.launch {
            startUseCase.expose(point).collect{
                _timerValue.value = it
            }
        }
    }
    fun pause(){
        pauseUseCase.expose()
    }
    fun resume(){
        resumeUseCase.expose()
    }
    fun finish(){
        finishUseCase.expose()
    }
    fun addOneMinute(){
        addTimeUseCase.expose(1L)
    }
    fun addTenMinutes(){
        addTimeUseCase.expose(10L)
    }
}