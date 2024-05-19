package ru.lanik.weatherapp.core

import android.location.Location
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.models.WeatherInfo
import java.time.LocalDateTime

interface ILocalStorage {
    var weatherTimestamp: LocalDateTime?
    val weatherUpdateIntervalMin: Int
    var cityName: String?
    var countryCode: String?
    var cityLocation: Location?
}

interface IGeocodingManager {
    suspend fun getCityInfo(forceNew: Boolean): CityInfo
}

interface IWeatherManager {
    suspend fun getWeatherInfo(forceNew: Boolean): WeatherInfo
}