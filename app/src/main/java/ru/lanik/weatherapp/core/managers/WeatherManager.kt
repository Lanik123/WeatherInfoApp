package ru.lanik.weatherapp.core.managers

import android.location.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.IWeatherManager
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.providers.DefaultLocationProvider
import ru.lanik.weatherapp.core.repository.WeatherRepository
import javax.inject.Inject

class WeatherManager @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val localStorage: ILocalStorage,
    private val defaultLocationProvider: DefaultLocationProvider,
) : IWeatherManager {
    override fun getWeatherInfo(coroutineScope: CoroutineScope, forceNew: Boolean): WeatherInfo? {
        coroutineScope.launch {
            val newLocation = defaultLocationProvider.getCurrentLocation() ?: localStorage.cityLocation

            newLocation?.let {
                return@let getWeatherInfoFromGps(newLocation)
            } ?: throw Exception("Couldn't retrieve location. Make sure to grant permission and enable GPS.")
        }
        return null
    }

    /*private fun isLocationSignificantChanged(oldLocation: Location, newLocation: Location): Boolean {
        val threshold = 0.3
        val distance = oldLocation.distanceTo(newLocation)
        return distance > threshold
    }*/

    /*
    fun isOldData(timestamp: LocalDateTime, intervalThreshold: Int): Boolean {
        val now = LocalDateTime.now()
        val duration = Duration.between(timestamp, now)
        return duration.toMinutes() >= intervalThreshold
    }*/

    private suspend fun getWeatherInfoFromGps(newLocation: Location): WeatherInfo {
        when (val result = weatherRepository.getWeatherData(newLocation.latitude, newLocation.longitude)) {
            is Resource.Success -> {
                return result.data!!
            }
            is Resource.Error -> {
                throw Exception(result.message)
            }
        }
    }
}