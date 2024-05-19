package ru.lanik.weatherapp.core.managers

import android.location.Location
import android.util.Log
import kotlinx.coroutines.delay
import ru.lanik.weatherapp.core.IGeocodingManager
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.providers.DefaultLocationProvider
import ru.lanik.weatherapp.core.repository.GeocodingRepository
import javax.inject.Inject

class GeocodingManager @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val localStorage: ILocalStorage,
    private val defaultLocationProvider: DefaultLocationProvider,
) : IGeocodingManager {
    override suspend fun getCityInfo(forceNew: Boolean): CityInfo {
        val newLocation = defaultLocationProvider.getCurrentLocation() ?: localStorage.cityLocation

        newLocation?.let {
            if (forceNew) {
                Log.e("GeocodingManager", "Force get new geocode")
                localStorage.cityLocation = newLocation
                return getCityInfoFromGps(newLocation)
            } else if (localStorage.cityName != null && localStorage.countryCode != null && localStorage.cityLocation != null) {
                if (isLocationSignificantChanged(localStorage.cityLocation!!, newLocation)){
                    Log.e("GeocodingManager", "LocationSignificantChanged")
                    localStorage.cityLocation = newLocation
                    return getCityInfoFromGps(newLocation)
                } else {
                    Log.e("GeocodingManager", "Get old code")
                    return CityInfo(
                        name = localStorage.cityName!!,
                        country = localStorage.countryCode!!,
                        lat = localStorage.cityLocation!!.latitude,
                        lon = localStorage.cityLocation!!.longitude,
                    )
                }
            } else {
                Log.e("GeocodingManager", "Get new code")
                localStorage.cityLocation = newLocation
                return getCityInfoFromGps(newLocation)
            }
        } ?: throw Exception("Couldn't retrieve location. Make sure to grant permission and enable GPS.")
    }

    private fun isLocationSignificantChanged(oldLocation: Location, newLocation: Location): Boolean {
        val threshold = 0.3
        val distance = oldLocation.distanceTo(newLocation)
        return distance > threshold
    }

    private suspend fun getCityInfoFromGps(newLocation: Location): CityInfo {
        when (val result = geocodingRepository.getCityName(newLocation.latitude, newLocation.longitude)) {
            is Resource.Success -> {
                localStorage.cityName = result.data?.name
                localStorage.countryCode = result.data?.country
                return CityInfo(
                    name = result.data?.name ?: "",
                    country = result.data?.country ?: "",
                    lat = newLocation.latitude,
                    lon = newLocation.longitude,
                )
            }
            is Resource.Error -> {
                throw Exception(result.message)
            }
        }
    }
}