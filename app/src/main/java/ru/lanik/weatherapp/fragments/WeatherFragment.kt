package ru.lanik.weatherapp.fragments

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import ru.lanik.weatherapp.core.BaseComposeFragment
import ru.lanik.weatherapp.ui.screen.WeatherScreen
import ru.lanik.weatherapp.ui.screen.WeatherViewModel

@AndroidEntryPoint
class WeatherFragment : BaseComposeFragment() {
    private val viewModel: WeatherViewModel by viewModels()

    @Composable
    override fun GetContent() {
        WeatherScreen(viewModel = viewModel)
    }
}