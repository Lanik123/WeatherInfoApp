package ru.lanik.weatherapp.core.repository

import android.app.Application
import ru.lanik.weatherapp.R
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.api.GeocodingApi
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.toCityInfo
import ru.lanik.weatherapp.core.toCityInfoList
import javax.inject.Inject

class GeocodingRepository @Inject constructor(
    private val api: GeocodingApi,
    application: Application,
) {
    private val key = application.getString(R.string.openweathermap_key)

    suspend fun getCityName(
        lat: Double,
        long: Double,
    ): Resource<CityInfo> {
        return try {
            Resource.Success(
                data =
                api.getCityName(
                    lat = lat,
                    lon = long,
                    appid = key,
                ).first().toCityInfo(lat, long),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Couldn't retrieve city name. Make sure your Network enabled.")
        }
    }

    suspend fun getCityCord(
        str: String,
    ): Resource<List<CityInfo>> {
        return try {
            Resource.Success(
                data =
                api.getCityCord(
                    q = str,
                    appid = key,
                ).toCityInfoList(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Couldn't retrieve city cord list. Make sure your Network enabled.")
        }
    }
}