package ru.lanik.weatherapp.core.repository

import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.api.WeatherApi
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.toWeatherInfo
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherApi,
) {
    suspend fun getWeatherData(
        lat: Double,
        long: Double,
    ): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data =
                api.getWeatherData(
                    lat = lat,
                    long = long,
                ).toWeatherInfo(),
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}