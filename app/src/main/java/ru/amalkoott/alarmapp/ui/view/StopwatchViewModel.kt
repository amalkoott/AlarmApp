package ru.amalkoott.alarmapp.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.ChronoTime
import ru.amalkoott.alarmapp.domain.usecase.GetRecordsStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.MarkRecordStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.ResetStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.StartStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.StopStopwatchUseCase
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val stopStopwatchUseCase: StopStopwatchUseCase,
    private val getRecordsStopwatchUseCase: GetRecordsStopwatchUseCase,
    private val markRecordStopwatchUseCase: MarkRecordStopwatchUseCase,
    private val resetStopwatchUseCase: ResetStopwatchUseCase
) : ViewModel() {
    private val _isStarted = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> get() = _isStarted

    private val _records = MutableStateFlow<List<StopwatchRecord>>(emptyList())
    val records: StateFlow<List<StopwatchRecord>> get() = _records

    var chronoTime = ChronoTime()

    init {
        viewModelScope.launch {
            getRecordsStopwatchUseCase.expose().collect{ record ->
                _records.value = record
            }
        }
    }

    fun start(){
        viewModelScope.launch {
            _isStarted.value = true
            val stopwatch = startStopwatchUseCase.expose()
            chronoTime = stopwatch
        }
    }

    fun stop(){
        _isStarted.value = false
        stopStopwatchUseCase.expose()
    }

    fun record(){
        markRecordStopwatchUseCase.expose()
    }

    fun reset(){
        _isStarted.value = false
        resetStopwatchUseCase.expose()
    }
}