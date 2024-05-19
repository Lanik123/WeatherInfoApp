package ru.lanik.weatherapp.core

import android.location.Location
import kotlinx.coroutines.CoroutineScope
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
    fun getCityInfo(coroutineScope: CoroutineScope, forceNew: Boolean): CityInfo?
}

interface IWeatherManager {
    fun getWeatherInfo(coroutineScope: CoroutineScope, forceNew: Boolean): WeatherInfo?
}