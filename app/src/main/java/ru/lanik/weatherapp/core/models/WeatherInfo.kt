package ru.lanik.weatherapp.core.models

data class WeatherInfo(
    val weatherUnitsData: WeatherUnitsData,
    val weatherDataPerDay: List<DailyWeatherData>,
    val currentWeatherData: CurrentWeatherData,
)