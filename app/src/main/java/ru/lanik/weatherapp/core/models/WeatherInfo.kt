package ru.lanik.weatherapp.core.models

data class WeatherInfo(
    val weatherDataPerDay: List<DailyWeatherData>,
    val currentWeatherData: CurrentWeatherData?,
)