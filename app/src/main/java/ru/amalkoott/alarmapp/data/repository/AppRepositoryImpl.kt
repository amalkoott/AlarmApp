package ru.amalkoott.alarmapp.data.repository

import ru.amalkoott.alarmapp.data.AppDao
import ru.amalkoott.alarmapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    appDao: AppDao
) : AppRepository {

}