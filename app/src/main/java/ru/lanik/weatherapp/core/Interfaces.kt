package ru.lanik.weatherapp.core

import android.location.Location

interface ILocationManager {
    suspend fun getCurrentLocation(): Location?
}