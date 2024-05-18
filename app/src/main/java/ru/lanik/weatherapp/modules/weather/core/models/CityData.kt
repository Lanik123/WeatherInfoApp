package ru.lanik.weatherapp.modules.weather.core.models

data class CityData(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
) {
    override fun toString(): String {
        return "$country, $name"
    }
}