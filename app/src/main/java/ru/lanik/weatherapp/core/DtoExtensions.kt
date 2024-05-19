package ru.lanik.weatherapp.core

import ru.lanik.weatherapp.core.api.dto.CityCordDto
import ru.lanik.weatherapp.core.api.dto.CityNameDto
import ru.lanik.weatherapp.core.api.dto.WeatherDto
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.models.WeatherData
import ru.lanik.weatherapp.core.models.WeatherInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData,
)

fun WeatherDto.WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperatures[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data =
            WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
            ),
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }
}

fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData =
        weatherDataMap[0]?.find {
            val hour = if (now.minute < 30) now.hour else now.hour + 1
            it.time.hour == hour
        }
    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData,
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