package ru.lanik.weatherapp.core.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityCordDto(
    val name: String,
    val country: String,
    val lat: Double,
    val lon: Double,
)
