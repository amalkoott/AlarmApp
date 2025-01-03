package ru.amalkoott.alarmapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.amalkoott.alarmapp.data.repository.AlarmManagerRepositoryImpl
import ru.amalkoott.alarmapp.data.repository.AlarmRepositoryImpl
import ru.amalkoott.alarmapp.data.repository.AppRepositoryImpl
import ru.amalkoott.alarmapp.data.repository.StopwatchRepositoryImpl
import ru.amalkoott.alarmapp.data.repository.TimerRepositoryImpl
import ru.amalkoott.alarmapp.data.supplier.StopwatchSupplierImpl
import ru.amalkoott.alarmapp.domain.repository.AlarmManagerRepository
import ru.amalkoott.alarmapp.domain.repository.AlarmRepository
import ru.amalkoott.alarmapp.domain.repository.AppRepository
import ru.amalkoott.alarmapp.domain.repository.StopwatchRepository
import ru.amalkoott.alarmapp.domain.repository.TimerRepository
import ru.amalkoott.alarmapp.domain.supplier.StopwatchSupplier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {
    @Singleton
    @Binds
    fun bindsAppRepository(repo: AppRepositoryImpl): AppRepository

    @Singleton
    @Binds
    fun bindStopwatchRepository(repo: StopwatchRepositoryImpl) : StopwatchRepository


    @Singleton
    @Binds
    fun bindStopwatchSupplier(supplier: StopwatchSupplierImpl) : StopwatchSupplier


    @Singleton
    @Binds
    fun bindTimerRepository(repo: TimerRepositoryImpl) : TimerRepository

    @Singleton
    @Binds
    fun bindAlarmRepository(repo: AlarmRepositoryImpl) : AlarmRepository

    @Singleton
    @Binds
    fun bindAlarmManagerRepository(repo: AlarmManagerRepositoryImpl) : AlarmManagerRepository
    /*
    @Singleton
    @Binds
    fun bindStopwatchService(service: StopwatchServiceImpl) : StopwatchService
    */
}