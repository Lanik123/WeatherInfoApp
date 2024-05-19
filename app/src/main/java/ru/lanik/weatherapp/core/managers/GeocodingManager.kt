package ru.lanik.weatherapp.core.managers

import android.location.Location
import android.util.Log
import ru.lanik.weatherapp.core.IGeocodingManager
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.providers.DefaultLocationProvider
import ru.lanik.weatherapp.core.providers.NetworkStateProvider
import ru.lanik.weatherapp.core.repository.GeocodingRepository
import javax.inject.Inject

class GeocodingManager @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val localStorage: ILocalStorage,
    private val defaultLocationProvider: DefaultLocationProvider,
    private val networkStateProvider: NetworkStateProvider,
) : IGeocodingManager {
    override suspend fun getCityInfo(forceNew: Boolean): Resource<CityInfo> {
        val newLocation = defaultLocationProvider.getCurrentLocation() ?: localStorage.cityLocation
        val networkState = networkStateProvider.isNetworkAvailable()

        if(!networkState) return Resource.Error("Your network connection is disabled. Please enable it before you start working with the application")

        newLocation?.let {
            if (forceNew) {
                Log.e("GeocodingManager", "Force get new geocode")
                localStorage.cityLocation = newLocation
                return geocodingRepository.getCityName(newLocation.latitude, newLocation.longitude)
            } else if (localStorage.cityName != null && localStorage.countryCode != null && localStorage.cityLocation != null) {
                if (isLocationSignificantChanged(localStorage.cityLocation!!, newLocation)) {
                    Log.e("GeocodingManager", "LocationSignificantChanged")
                    localStorage.cityLocation = newLocation
                    return geocodingRepository.getCityName(newLocation.latitude, newLocation.longitude)
                } else {
                    Log.e("GeocodingManager", "Get old code")
                    return Resource.Success(
                        data = CityInfo(
                            name = localStorage.cityName!!,
                            country = localStorage.countryCode!!,
                            lat = localStorage.cityLocation!!.latitude,
                            lon = localStorage.cityLocation!!.longitude,
                        )
                    )
                }
            } else {
                Log.e("GeocodingManager", "Get new code")
                localStorage.cityLocation = newLocation
                return geocodingRepository.getCityName(newLocation.latitude, newLocation.longitude)
            }
        } ?: return Resource.Error("Couldn't retrieve location. Make sure you grant all permission and enable GPS.")
    }

    private fun isLocationSignificantChanged(oldLocation: Location, newLocation: Location): Boolean {
        val threshold = 0.3
        val distance = oldLocation.distanceTo(newLocation)
        return distance > threshold
    }
}