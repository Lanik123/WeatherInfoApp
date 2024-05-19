package ru.lanik.weatherapp.core.models

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyWeatherData(
    val time: LocalDate = LocalDate.now(),
    val temperatureMin: Double = 0.0,
    val temperatureMax: Double = 0.0,
    val weatherType: WeatherType = WeatherType.fromWMO(1),
)
