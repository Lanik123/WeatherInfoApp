package ru.lanik.weatherapp.core.models

import kotlinx.serialization.Serializable

@Serializable
data class LocalDataState(
    var weatherTimestamp: String? = null,
    val weatherUpdateIntervalMin: Int = 30,
    var cityName: String? = null,
    var countryCode: String? = null,
    var cityLat: Double? = null,
    var cityLon: Double? = null,
)
