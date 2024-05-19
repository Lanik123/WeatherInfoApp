package ru.lanik.weatherapp.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import ru.lanik.weatherapp.core.models.LocalDataState
import ru.lanik.weatherapp.modules.weather.core.api.GeocodingApi
import ru.lanik.weatherapp.modules.weather.core.api.WeatherApi
import java.io.InputStream
import java.io.OutputStream
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
    fun provideLocalDataStateSerializer(): Serializer<LocalDataState> =
        object : Serializer<LocalDataState> {
            override val defaultValue: LocalDataState = LocalDataState()

            override suspend fun readFrom(input: InputStream): LocalDataState {
                try {
                    return Json.decodeFromString(
                        LocalDataState.serializer(),
                        input.readBytes().decodeToString(),
                    )
                } catch (serialization: SerializationException) {
                    throw CorruptionException("Unable to read local data", serialization)
                }
            }

            override suspend fun writeTo(
                t: LocalDataState,
                output: OutputStream,
            ) {
                withContext(Dispatchers.IO) {
                    output.write(
                        Json.encodeToString(LocalDataState.serializer(), t)
                            .encodeToByteArray(),
                    )
                }
            }
        }

    @Provides
    @Singleton
    fun provideLocalDataStore(
        @ApplicationContext context: Context,
        localDataStateSerializer: Serializer<LocalDataState>,
    ): DataStore<LocalDataState> =
        DataStoreFactory.create(
            serializer = localDataStateSerializer,
            scope = CoroutineScope(Dispatchers.IO + Job()),
            produceFile = { context.dataStoreFile("data.json") },
        )

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