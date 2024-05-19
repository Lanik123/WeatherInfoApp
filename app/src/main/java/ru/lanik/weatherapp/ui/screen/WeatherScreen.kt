package ru.lanik.weatherapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(it)) {
            if (viewState.isLoading) {
                Text("Loading", color = Color.White)
            } else if (viewState.weatherInfo != null && viewState.cityInfo != null) {
                Text("City = ${viewState.cityInfo?.name}", color = Color.White)
                Text("Time = ${viewState.weatherInfo?.currentWeatherData?.time}", color = Color.White)
                Text("Temp = ${viewState.weatherInfo?.currentWeatherData?.temperatureCelsius}", color = Color.White)
            }
        }
    }
}