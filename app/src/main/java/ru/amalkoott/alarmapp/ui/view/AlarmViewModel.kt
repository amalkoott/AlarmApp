package ru.amalkoott.alarmapp.ui.view

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.amalkoott.alarmapp.domain.model.Alarm
import ru.amalkoott.alarmapp.domain.usecase.alarm.GetAlarmsUseCase
import ru.amalkoott.alarmapp.domain.usecase.alarm.SwitchAlarmUseCase
import ru.amalkoott.alarmapp.domain.usecase.alarm.UpdateAlarmUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val getAlarmsUseCase: GetAlarmsUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase,
    private val switchAlarmUseCase: SwitchAlarmUseCase
) : ViewModel() {

    private val _alrms = MutableStateFlow<List<State<Alarm>>>(emptyList())
    val alrms: MutableStateFlow<List<State<Alarm>>> get() = _alrms

    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> get() = _alarms

    var selectedAlarm = mutableStateOf<Alarm?>(null)
    var isExposed = mutableStateOf(false)

    init {
        viewModelScope.launch {
            getAlarmsUseCase.expose().collect{
                val mutableStateList = it.map { alarm ->
                    mutableStateOf(alarm)
                }
                _alrms.value = mutableStateList
                _alarms.value = it
            }
        }
    }
    fun getAlarms(): MutableStateFlow<List<Alarm>>{
        return _alarms
    }
    fun onSelectClick(value: Alarm?){
        if(selectedAlarm.value == value){
            selectedAlarm.value = null
        }

        selectedAlarm.value = value
    }
    fun onDeselectClick(){
        selectedAlarm.value = null
    }
    fun onSwitchAlarm(alarm: State<Alarm>){
        val updatedAlarm = alarm.value

        //if (selectedAlarm.value !== alarm.value) selectedAlarm.value = null

        viewModelScope.launch {
            //selectedAlarm.value = updatedAlarm
            updatedAlarm.isActive = !updatedAlarm.isActive
            updateAlarmUseCase.expose(updatedAlarm)
            switchAlarmUseCase.expose(updatedAlarm)
        }
    }
}