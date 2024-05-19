package ru.lanik.weatherapp.core.models

import java.time.LocalDateTime

data class CurrentWeatherData(
    val time: LocalDateTime,
    val temperature: Double,
    val apTemperature: Double,
    val pressure: Double,
    val windSpeed: Double,
    val humidity: Double,
    // val weatherType: WeatherType
)