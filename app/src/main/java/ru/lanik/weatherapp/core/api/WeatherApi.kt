package ru.lanik.weatherapp.core.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.lanik.weatherapp.core.api.dto.WeatherDto

interface WeatherApi {
    @GET("v1/forecast?current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,wind_speed_10m,pressure_msl&daily=weather_code,temperature_2m_max,temperature_2m_min&forecast_days=14")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
    ): WeatherDto
}