package ru.lanik.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.lanik.weatherapp.core.ILocationManager
import ru.lanik.weatherapp.core.managers.DefaultLocationManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagersModule {
    @Binds
    @Singleton
    abstract fun bindLocationManager(defaultLocationManager: DefaultLocationManager): ILocationManager
}