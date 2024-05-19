package ru.lanik.weatherapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.lanik.weatherapp.core.IGeocodingManager
import ru.lanik.weatherapp.core.IWeatherManager
import ru.lanik.weatherapp.core.Resource
import ru.lanik.weatherapp.core.models.ScreenState
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
        viewModelScope.launch {
            viewState.collect {
                if (it.cityInfo != null && it.weatherInfo != null) {
                    changeState(ScreenState.ShowForecast)
                } else if (it.cityInfo == null && it.weatherInfo == null && it.errorMessage.isNotEmpty()) {
                    changeState(ScreenState.Error)
                } else if (it.cityInfo == null && it.weatherInfo == null) {
                    changeState(ScreenState.Loading)
                }
            }
        }
    }

    fun onRefreshClick(forceUpdate: Boolean) {
        onCitySync(forceUpdate)
        onWeatherSync(forceUpdate)
        clearError()
    }

    private fun onWeatherSync(forceUpdate: Boolean) {
        viewModelScope.launch {
            when (val result = weatherManager.getWeatherInfo(forceUpdate)) {
                is Resource.Success -> {
                    _viewState.value =
                        _viewState.value.copy(
                            weatherInfo = result.data,
                        )
                }
                is Resource.Error -> {
                    addError(result.message)
                }
            }
        }
    }

    private fun onCitySync(forceUpdate: Boolean) {
        viewModelScope.launch {
            delay(200L)
            when (val result = geocodingManager.getCityInfo(forceUpdate)) {
                is Resource.Success -> {
                    _viewState.value =
                        _viewState.value.copy(
                            cityInfo = result.data,
                        )
                }
                is Resource.Error -> {
                    addError(result.message)
                }
            }
        }
    }

    private fun changeState(newState: ScreenState) {
        if (_viewState.value.state != newState) {
            _viewState.value =
                _viewState.value.copy(
                    state = newState,
                )
        }
    }

    private fun addError(str: String?) {
        str?.let {
            if (!_viewState.value.errorMessage.contains(str)) {
                _viewState.value =
                    _viewState.value.copy(
                        errorMessage = "${_viewState.value.errorMessage}\n\n\n$str",
                    )
            }
        }
    }

    private fun clearError() {
        _viewState.value =
            _viewState.value.copy(
                errorMessage = "",
            )
    }
}