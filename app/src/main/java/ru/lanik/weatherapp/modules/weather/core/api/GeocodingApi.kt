package ru.lanik.weatherapp.modules.weather.core.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.lanik.weatherapp.modules.weather.core.api.dto.CityCordDto
import ru.lanik.weatherapp.modules.weather.core.api.dto.CityNameDto

interface GeocodingApi {
    @GET("geo/1.0/reverse?")
    suspend fun getCityName(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
    ): List<CityNameDto>

    @GET("geo/1.0/direct?limit=5")
    suspend fun getCityCord(
        @Query("q") q: String,
        @Query("appid") appid: String,
    ): List<CityCordDto>
}