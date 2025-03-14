package ru.lanik.weatherapp.core.managers

import android.location.Location
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

        if (!networkState) return Resource.Error("Your network connection is disabled. Please enable it before you start working with the application")

        newLocation?.let {
            if (forceNew) {
                localStorage.cityLocation = newLocation
                return getCityNameAndCaching(newLocation.latitude, newLocation.longitude)
            } else if (localStorage.cityName != null && localStorage.countryCode != null && localStorage.cityLocation != null) {
                if (isLocationSignificantChanged(localStorage.cityLocation!!, newLocation)) {
                    localStorage.cityLocation = newLocation
                    return getCityNameAndCaching(newLocation.latitude, newLocation.longitude)
                } else {
                    return Resource.Success(
                        data = CityInfo(
                            name = localStorage.cityName!!,
                            country = localStorage.countryCode!!,
                            lat = localStorage.cityLocation!!.latitude,
                            lon = localStorage.cityLocation!!.longitude,
                        ),
                    )
                }
            } else {
                localStorage.cityLocation = newLocation
                return getCityNameAndCaching(newLocation.latitude, newLocation.longitude)
            }
        } ?: return Resource.Error("Couldn't retrieve information. Make sure you grant all permission and enable GPS.")
    }

    private suspend fun getCityNameAndCaching(
        lat: Double,
        long: Double,
    ): Resource<CityInfo> {
        when (val result = geocodingRepository.getCityName(lat, long)) {
            is Resource.Success -> {
                localStorage.cityName = result.data?.name
                localStorage.countryCode = result.data?.country
                return result
            }
            is Resource.Error -> {
                return result
            }
        }
    }

    private fun isLocationSignificantChanged(oldLocation: Location, newLocation: Location): Boolean {
        val threshold = 0.3
        val distance = oldLocation.distanceTo(newLocation)
        return distance > threshold
    }
}