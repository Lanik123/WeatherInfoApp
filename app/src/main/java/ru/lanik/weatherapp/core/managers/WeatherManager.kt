package ru.lanik.weatherapp.core.managers

import android.location.Location
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.IWeatherManager
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.providers.DefaultLocationProvider
import ru.lanik.weatherapp.core.providers.NetworkStateProvider
import ru.lanik.weatherapp.core.repository.WeatherRepository
import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val localStorage: ILocalStorage,
    private val defaultLocationProvider: DefaultLocationProvider,
    private val networkStateProvider: NetworkStateProvider,
) : IWeatherManager {
    override suspend fun getWeatherInfo(forceNew: Boolean): Resource<WeatherInfo> {
        val newLocation = defaultLocationProvider.getCurrentLocation() ?: localStorage.cityLocation
        val networkState = networkStateProvider.isNetworkAvailable()

        if(!networkState) return Resource.Error("Your network connection is disabled. Please enable it before you start working with the application")
        newLocation?.let {
            return weatherRepository.getWeatherData(newLocation.latitude, newLocation.longitude)
        } ?: return Resource.Error("Couldn't retrieve weather info. Make sure you grant all permission and enable GPS.")
    }
}