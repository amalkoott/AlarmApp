package ru.amalkoott.alarmapp.data

import ru.amalkoott.alarmapp.domain.AppRepository
import javax.inject.Inject

class AppRepository_Impl @Inject constructor(
    dao: Dao
) : AppRepository {
}