package ru.lanik.weatherapp.core.models

data class WeatherViewState(
    val cityInfo: CityInfo? = null,
    val weatherInfo: WeatherInfo? = null,
    val errorMessage: String = "",
    val state: ScreenState = ScreenState.Loading,
)

enum class ScreenState {
    Loading,
    ShowForecast,
    Error,
}