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
    fun provideAppDao(db: AppDatabase): Dao {
        return db.getDao()
    }

}