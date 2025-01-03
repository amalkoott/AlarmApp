package ru.amalkoott.alarmapp.domain.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChronoTime(
    //private var hours: Long = 0L,
    //private var minutes: Long = 0L,
    private var seconds: Flow<Long> = MutableStateFlow(0L),
    private var milliseconds: Flow<Long> = MutableStateFlow(0L),
) {
    constructor(seconds: Flow<Long>) : this() {
        this.seconds = seconds
    }
    constructor(hours: Long, minutes: Long, seconds: Long): this(){
        val timeInSeconds = hours * 60 * 60 + minutes * 60 + seconds
        this.seconds = MutableStateFlow(timeInSeconds)
    }

    private val _secondsFlow = MutableStateFlow(0L)
    private val _millisecondsFlow = MutableStateFlow(0L)
    init {
        collectSeconds()
        collectMilliseconds()
    }
    private fun collectSeconds(){
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            seconds.collect{
                _secondsFlow.value = it
            }
        }
    }
    private fun collectMilliseconds(){
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            milliseconds.collect{
                _millisecondsFlow.value = it
            }
        }
    }

    fun getSeconds():StateFlow<Long>{
        val seconds: StateFlow<Long> = _secondsFlow
        return seconds
    }
    fun getMilliseconds():StateFlow<Long>{
        val milliseconds: StateFlow<Long> = _millisecondsFlow
        return milliseconds
    }
}