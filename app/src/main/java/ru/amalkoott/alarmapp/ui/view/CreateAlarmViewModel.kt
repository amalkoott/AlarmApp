package ru.amalkoott.alarmapp.ui.view

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.usecase.alarm.SaveUseCase
import javax.inject.Inject

@HiltViewModel
class CreateAlarmViewModel @Inject constructor(
    private val saveUseCase: SaveUseCase
) : ViewModel() {
    private val _selectedAlarm = mutableStateOf(Alarm.EMPTY_ALARM)
    val selectedAlarm: State<Alarm> get() = _selectedAlarm

    fun onTimeConfirm(hour: Int, minute: Int){
        _selectedAlarm.value.time = minute
    }
    fun onTimeDismiss(){
        Log.d("ALARM","time dismissed")
    }
    fun onDateConfirm(value: Long){
        Log.d("ALARM","date confirmed")
    }
    fun onDateDismiss(){
        Log.d("ALARM","time dismissed")
    }

    fun add(){
        viewModelScope.launch {
            saveUseCase.expose(_selectedAlarm.value)
        }
        _selectedAlarm.value = Alarm.EMPTY_ALARM
        Log.d("ALARM","alarm added : ${selectedAlarm.value}")


    }
}