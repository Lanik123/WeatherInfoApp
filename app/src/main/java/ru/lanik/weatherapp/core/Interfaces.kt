package ru.lanik.weatherapp.core

import android.location.Location
import java.time.LocalDateTime

interface ILocalStorage {
    var weatherTimestamp: LocalDateTime?
    val weatherUpdateIntervalMin: Int
    var cityName: String?
    var countryCode: String?
    var cityLocation: Location?

}