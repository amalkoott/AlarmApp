package ru.amalkoott.alarmapp.ui.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.model.StopwatchRecord
import ru.amalkoott.alarmapp.domain.model.Time
import ru.amalkoott.alarmapp.domain.usecase.GetRecordsStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.MarkRecordStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.StartStopwatchUseCase
import ru.amalkoott.alarmapp.domain.usecase.StopStopwatchUseCase
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val startStopwatchUseCase: StartStopwatchUseCase,
    private val stopStopwatchUseCase: StopStopwatchUseCase,
    private val getRecordsStopwatchUseCase: GetRecordsStopwatchUseCase,
    private val markRecordStopwatchUseCase: MarkRecordStopwatchUseCase
) : ViewModel() {
    private val _isStarted = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> get() = _isStarted

    private val _records = MutableStateFlow<List<StopwatchRecord>>(emptyList())
    val records: StateFlow<List<StopwatchRecord>> get() = _records

    var temp_time = MutableStateFlow<Long>(0L)
    //var time = MutableStateFlow<Time>(Time.ZERO)

    //private val _secondsFlow = MutableStateFlow(0L)
    //private val _millisecondsFlow = MutableStateFlow(0L)
    var time = Time()

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
            time = stopwatch
            /*
            launch {
                stopwatch.seconds.collect{
                   // _secondsFlow.value = it
                    time.collectSeconds(it)
                }
            }
            launch {
                stopwatch.milliseconds.collect{
                   // _millisecondsFlow.value = it
                    time.collectMilliseconds(it)
                }
            }
            */
            /*
            startStopwatchUseCase.expose().collect{
                //temp_time.value = it
                time.value = it
            }
            */
        }
    }

    fun stop(){
        _isStarted.value = false
        stopStopwatchUseCase.expose()
    }

    fun record(){
        markRecordStopwatchUseCase.expose()
    }

    fun remove(){
        _isStarted.value = true
    }
}