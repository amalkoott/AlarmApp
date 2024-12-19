package ru.amalkoott.alarmapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.amalkoott.alarmapp.domain.model.Alarm


@Database(
    entities = [Alarm::class],
    version = 2,
    exportSchema = false
)

//@TypeConverters(AdvertsListConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): AppDao

}
