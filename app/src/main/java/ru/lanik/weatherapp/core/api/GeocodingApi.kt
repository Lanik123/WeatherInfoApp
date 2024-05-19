package ru.lanik.weatherapp.core.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.lanik.weatherapp.core.api.dto.CityNameDto

interface GeocodingApi {
    @GET("geo/1.0/reverse?")
    suspend fun getCityName(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
    ): List<CityNameDto>
}