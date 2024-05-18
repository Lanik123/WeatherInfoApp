package ru.lanik.weatherapp.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import ru.lanik.weatherapp.modules.weather.core.api.GeocodingApi
import ru.lanik.weatherapp.modules.weather.core.api.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val format =
        Json {
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun provideMediaType(): MediaType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideWeatherApi(type: MediaType): WeatherApi =
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(format.asConverterFactory(type))
            .build()
            .create()

    @Provides
    @Singleton
    fun provideGeocodingApi(type: MediaType): GeocodingApi =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(format.asConverterFactory(type))
            .build()
            .create()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}