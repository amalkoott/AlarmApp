package ru.amalkoott.alarmapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.amalkoott.alarmapp.domain.model.Alarm

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(alarm: Alarm): Long

    @Query("SELECT * FROM Alarm ORDER BY id ASC")
    fun all(): Flow<List<Alarm>>

    @Query("DELETE FROM Alarm")
    fun clearAll()

    @Update
    fun update(alarm: Alarm): Int
}