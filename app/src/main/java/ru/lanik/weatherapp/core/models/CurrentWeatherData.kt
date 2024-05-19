package ru.lanik.weatherapp.core.models

import java.time.LocalDateTime

data class CurrentWeatherData(
    val time: LocalDateTime = LocalDateTime.now(),
    val temperature: Double = 0.0,
    val apTemperature: Double = 0.0,
    val pressure: Double = 0.0,
    val windSpeed: Double = 0.0,
    val humidity: Double = 0.0,
    val weatherType: WeatherType = WeatherType.fromWMO(1),
)