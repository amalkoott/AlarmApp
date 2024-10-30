package ru.amalkoott.alarmapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.amalkoott.alarmapp.domain.entity.Alarm


@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = false
)

//@TypeConverters(AdvertsListConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getDao(): Dao

}
