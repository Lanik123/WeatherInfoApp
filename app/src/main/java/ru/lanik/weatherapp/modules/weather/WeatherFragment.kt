package ru.lanik.weatherapp.modules.weather

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lanik.weatherapp.core.BaseComposeFragment

@AndroidEntryPoint
class WeatherFragment : BaseComposeFragment() {
    private val viewModel: WeatherViewModel by viewModels()

    @Composable
    override fun GetContent(navController: NavController) {
        WeatherScreen(navController = navController, viewModel = viewModel)
    }
}