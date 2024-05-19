package ru.lanik.weatherapp.core.managers

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import ru.lanik.weatherapp.core.ILocalStorage
import ru.lanik.weatherapp.core.models.LocalDataState
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

    override var cityName: String?
        get() = localDataState.value.cityName
        set(value) {
            writeData(
                localDataState.value.copy(
                    cityName = value,
                ),
            )
        }
    override var cityLat: Double?
        get() = localDataState.value.cityLat
        set(value) {
            writeData(
                localDataState.value.copy(
                    cityLat = value,
                ),
            )
        }
    override var cityLon: Double?
        get() = localDataState.value.cityLon
        set(value) {
            writeData(
                localDataState.value.copy(
                    cityLon = value,
                ),
            )
        }

    private fun writeData(newModel: LocalDataState) {
        localCoroutineScope.launch {
            localDataStore.updateData { newModel }
        }
    }
}