package ru.lanik.weatherapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.lanik.weatherapp.core.IGeocodingManager
import ru.lanik.weatherapp.core.IWeatherManager
import ru.lanik.weatherapp.core.managers.GeocodingManager
import ru.lanik.weatherapp.core.managers.WeatherManager
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.models.WeatherViewState
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val geocodingManager: IGeocodingManager,
    private val weatherManager: IWeatherManager,
) : ViewModel() {
    private val _viewState = MutableStateFlow(WeatherViewState())
    val viewState = _viewState.asStateFlow()

    init {
        var cityInfo: CityInfo? = null
        var weatherInfo: WeatherInfo? = null
        var errorMessage: String? = null

        viewModelScope.launch {
            _viewState.value =
                _viewState.value.copy(
                    isLoading = true,
                    error = null,
                )

            try {
                cityInfo = geocodingManager.getCityInfo(false)
            } catch (e: Exception) {
                errorMessage = e.message
            }

            try {
                weatherInfo = weatherManager.getWeatherInfo(false)
            } catch (e: Exception) {
                errorMessage = e.message
            }

            _viewState.value =
                _viewState.value.copy(
                    cityInfo = cityInfo,
                    weatherInfo = weatherInfo,
                    isLoading = false,
                    error = errorMessage,
                )
        }
    }
}