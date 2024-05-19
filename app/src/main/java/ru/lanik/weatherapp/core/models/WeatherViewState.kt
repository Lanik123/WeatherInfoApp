package ru.lanik.weatherapp.core.models

data class WeatherViewState(
    val cityInfo: CityInfo? = null,
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)