package ru.lanik.weatherapp.modules.weather.core.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityNameDto(
    val name: String,
)
