package ru.amalkoott.alarmapp.ui.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.amalkoott.alarmapp.domain.AppUseCase
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    useCase: AppUseCase
) : ViewModel() {
}