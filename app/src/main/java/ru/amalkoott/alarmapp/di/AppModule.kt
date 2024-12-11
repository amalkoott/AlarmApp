package ru.amalkoott.alarmapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.amalkoott.alarmapp.data.AppDatabase
import ru.amalkoott.alarmapp.data.Dao
import ru.amalkoott.alarmapp.data.supplier.StopwatchSupplierImpl
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Singleton
import kotlin.contracts.contract

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
    fun provideAppDao(db: AppDatabase): Dao {
        return db.getDao()
    }

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