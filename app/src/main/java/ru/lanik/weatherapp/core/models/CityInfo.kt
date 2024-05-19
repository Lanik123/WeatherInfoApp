package ru.lanik.weatherapp.core.models

data class CityInfo(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
) {
    override fun toString(): String {
        return "$country, $name"
    }
}