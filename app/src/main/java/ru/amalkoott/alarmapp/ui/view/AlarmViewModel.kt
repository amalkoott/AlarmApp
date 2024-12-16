package ru.amalkoott.alarmapp.ui.view

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.usecase.AppUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    useCase: AppUseCase
) : ViewModel() {

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> get() = _alarms

    var selectedAlarm = mutableStateOf<Alarm?>(null)

    fun onAddClick(){
        selectedAlarm.value = Alarm.EMPTY_ALARM
    }
    fun onSaveClick(){
        selectedAlarm.value = null
    }
}