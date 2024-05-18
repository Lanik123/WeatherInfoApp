package ru.lanik.weatherapp.modules.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun WeatherScreen(
    navController: NavController,
    viewModel: WeatherViewModel,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Text("Hello world", color = Color.White, modifier = Modifier.padding(it))
    }
}