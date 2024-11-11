package ru.amalkoott.alarmapp.data.repository

import ru.amalkoott.alarmapp.data.Dao
import ru.amalkoott.alarmapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    dao: Dao
) : AppRepository {
}