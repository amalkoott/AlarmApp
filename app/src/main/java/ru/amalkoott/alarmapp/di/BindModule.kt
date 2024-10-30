package ru.amalkoott.alarmapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.amalkoott.alarmapp.data.AppRepository_Impl
import ru.amalkoott.alarmapp.domain.AppRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {
    @Singleton
    @Binds
    fun bindsAppRepository(repo: AppRepository_Impl): AppRepository
}