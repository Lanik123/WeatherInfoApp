package ru.lanik.weatherapp.core.models

import java.time.LocalDate

data class DailyWeatherData(
    val time: LocalDate,
    val temperatureMin: Double,
    val temperatureMax: Double,
    // val weatherType: WeatherType
)
