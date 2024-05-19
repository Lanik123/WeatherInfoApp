package ru.lanik.weatherapp.core.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalDataState(
    var cityName: String? = null,
    var cityLat: Double? = null,
    var cityLon: Double? = null,
)
