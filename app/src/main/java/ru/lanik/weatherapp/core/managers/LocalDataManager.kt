package ru.lanik.weatherapp.core.managers

import android.location.Location
import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.models.LocalDataState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDataManager @Inject constructor(
    private val localDataStore: DataStore<LocalDataState>,
) : ILocalStorage {
    private val localCoroutineScope = CoroutineScope(Dispatchers.IO + Job())
    private var localDataState: MutableStateFlow<LocalDataState>

    init {
        localDataStore.data.onEmpty {
            localDataStore.updateData { LocalDataState() }
        }
        val state: MutableStateFlow<LocalDataState> by lazy {
            val data = MutableStateFlow(LocalDataState())
            localCoroutineScope.launch {
                localDataStore.data.collect { newValue ->
                    data.value = newValue
                }
            }
            return@lazy data
        }
        localDataState = state
    }

    override var weatherTimestamp: LocalDateTime?
        get() = LocalDateTime.parse(localDataState.value.weatherTimestamp, DateTimeFormatter.ISO_DATE_TIME)
        set(value) {
            writeData(
                localDataState.value.copy(
                    weatherTimestamp = value.toString(),
                ),
            )
        }
    override val weatherUpdateIntervalMin: Int
        get() = localDataState.value.weatherUpdateIntervalMin

    override var cityName: String?
        get() = localDataState.value.cityName
        set(value) {
            writeData(
                localDataState.value.copy(
                    cityName = value,
                ),
            )
        }
    override var countryCode: String?
        get() = localDataState.value.countryCode
        set(value) {
            writeData(
                localDataState.value.copy(
                    countryCode = value,
                ),
            )
        }
    override var cityLocation: Location?
        get() {
            val newLocation = createLocationFromCoordinates(localDataState.value.cityLat, localDataState.value.cityLon)
            return newLocation
        }
        set(value) {
            writeData(
                localDataState.value.copy(
                    cityLat = value?.latitude,
                    cityLon = value?.longitude,
                ),
            )
        }

    private fun createLocationFromCoordinates(latitude: Double?, longitude: Double?): Location? {
        return if (latitude != null && longitude != null) {
            Location("manual").apply {
                this.latitude = latitude
                this.longitude = longitude
            }
        } else {
            null
        }
    }

    private fun writeData(newModel: LocalDataState) {
        runBlocking {
            localDataStore.updateData { newModel }
        }
    }
}