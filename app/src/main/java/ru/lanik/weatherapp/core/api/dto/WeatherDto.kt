package ru.lanik.weatherapp.core.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("current")
    val currentData: CurrentWeatherDataDto,
    @SerialName("daily")
    val dailyData: DailyWeatherDataDto,
) {
    @Serializable
    data class CurrentWeatherDataDto(
        val time: String,
        @SerialName("temperature_2m")
        val temperature: Double,
        @SerialName("apparent_temperature")
        val apTemperature: Double,
        @SerialName("pressure_msl")
        val pressure: Double,
        @SerialName("wind_speed_10m")
        val windSpeed: Double,
        @SerialName("relative_humidity_2m")
        val humiditiy: Double,
        @SerialName("weather_code")
        val weatherCode: Int,
    )

    @Serializable
    data class DailyWeatherDataDto(
        val time: List<String>,
        @SerialName("temperature_2m_min")
        val temperatureMin: List<Double>,
        @SerialName("temperature_2m_max")
        val temperatureMax: List<Double>,
        @SerialName("weather_code")
        val weatherCode: List<Int>,
    )
}