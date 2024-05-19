package ru.lanik.weatherapp.core

import android.location.Location

interface ILocationManager {
    suspend fun getCurrentLocation(): Location?
}

interface ILocalStorage {
    var cityName: String?
    var cityLat: Double?
    var cityLon: Double?
}