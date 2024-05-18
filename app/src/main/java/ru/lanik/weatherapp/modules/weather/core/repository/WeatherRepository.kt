package ru.lanik.weatherapp.modules.weather.core.repository

import ru.lanik.weatherapp.modules.weather.core.Resource
import ru.lanik.weatherapp.modules.weather.core.api.WeatherApi
import ru.lanik.weatherapp.modules.weather.core.models.WeatherInfo
import ru.lanik.weatherapp.modules.weather.core.toWeatherInfo
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