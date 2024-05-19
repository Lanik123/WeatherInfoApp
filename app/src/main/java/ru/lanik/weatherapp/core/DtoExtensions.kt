package ru.lanik.weatherapp.core

import ru.lanik.weatherapp.core.api.dto.CityCordDto
import ru.lanik.weatherapp.core.api.dto.CityNameDto
import ru.lanik.weatherapp.core.api.dto.WeatherDto
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.models.CurrentWeatherData
import ru.lanik.weatherapp.core.models.DailyWeatherData
import ru.lanik.weatherapp.core.models.WeatherInfo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val currentWeatherData = CurrentWeatherData(
        time = LocalDateTime.parse(this.currentData.time, DateTimeFormatter.ISO_DATE_TIME),
        temperature = this.currentData.temperature,
        apTemperature = this.currentData.apTemperature,
        pressure = this.currentData.pressure,
        windSpeed = this.currentData.windSpeed,
        humidity = this.currentData.humiditiy,
    )

    val dailyWeatherData = List(this.dailyData.weatherCode.size) { index ->
        DailyWeatherData(
            time = LocalDate.parse(this.dailyData.time[index]),
            temperatureMin = this.dailyData.temperatureMin[index],
            temperatureMax = this.dailyData.temperatureMax[index],
        )
    }

    return WeatherInfo(
        currentWeatherData = currentWeatherData,
        weatherDataPerDay = dailyWeatherData,
    )
}

fun List<CityCordDto>.toCityInfoList(): List<CityInfo> {
    return this.map {
        CityInfo(
            name = it.name,
            country = it.name,
            lat = it.lat,
            lon = it.lon,
        )
    }
}

fun CityNameDto.toCityInfo(lat: Double, long: Double): CityInfo {
    return CityInfo(
        name = name,
        country = country,
        lat = lat,
        lon = long,
    )
}