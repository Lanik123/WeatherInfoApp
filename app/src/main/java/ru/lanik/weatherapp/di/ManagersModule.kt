package ru.lanik.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.lanik.weatherapp.core.IGeocodingManager
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.IWeatherManager
import ru.lanik.weatherapp.core.managers.GeocodingManager
import ru.lanik.weatherapp.core.managers.LocalDataManager
import ru.lanik.weatherapp.core.managers.WeatherManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagersModule {
    @Binds
    @Singleton
    abstract fun bindLocalDataManager(localDataManager: LocalDataManager): ILocalStorage

    @Binds
    @Singleton
    abstract fun bindGeocodingManager(geocodingManager: GeocodingManager): IGeocodingManager

    @Binds
    @Singleton
    abstract fun bindWeatherManager(weatherManager: WeatherManager): IWeatherManager
}