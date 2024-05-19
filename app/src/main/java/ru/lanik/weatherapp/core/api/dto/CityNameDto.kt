package ru.lanik.weatherapp.core.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityNameDto(
    val name: String,
    val country: String,
)
