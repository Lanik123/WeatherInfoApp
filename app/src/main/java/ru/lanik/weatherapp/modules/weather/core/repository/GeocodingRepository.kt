package ru.lanik.weatherapp.modules.weather.core.repository

import android.app.Application
import ru.lanik.weatherapp.R
import ru.lanik.weatherapp.modules.weather.core.Resource
import ru.lanik.weatherapp.modules.weather.core.api.GeocodingApi
import ru.lanik.weatherapp.modules.weather.core.models.CityData
import ru.lanik.weatherapp.modules.weather.core.toCityDataList
import javax.inject.Inject

class GeocodingRepository @Inject constructor(
    private val api: GeocodingApi,
    application: Application,
) {
    private val key = application.getString(R.string.openweathermap_key)

    suspend fun getCityName(
        lat: Double,
        long: Double,
    ): Resource<String> {
        return try {
            Resource.Success(
                data =
                api.getCityName(
                    lat = lat,
                    lon = long,
                    appid = key,
                ).first().name,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    suspend fun getCityCord(
        str: String,
    ): Resource<List<CityData>> {
        return try {
            Resource.Success(
                data =
                api.getCityCord(
                    q = str,
                    appid = key,
                ).toCityDataList(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}