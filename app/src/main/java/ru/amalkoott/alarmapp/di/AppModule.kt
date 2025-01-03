package ru.amalkoott.alarmapp.di

import android.app.AlarmManager
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.amalkoott.alarmapp.data.AppDatabase
import ru.amalkoott.alarmapp.data.AppDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,"app_database"
        ).fallbackToDestructiveMigration()
            .build()
        return db
    }

    @Singleton
    @Provides
    fun provideAppDao(db: AppDatabase): AppDao {
        return db.getDao()
    }

    @Singleton
    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager{
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context{
        return context
    }
    /*
    @Singleton
    @Provides
    fun provideTimerService(): TimerService{
        return TimerService()
    }
    */
    /*
    @Provides
    fun provideStopwatchSupplier(@ApplicationContext context: Context): StopwatchSupplier{
        return StopwatchSupplierImpl(context)
    }

    @Provides
    fun provideStopwatchService(): StopwatchService {
        return StopwatchServiceImpl()
    }
    */
}