package ru.lanik.weatherapp.fragments

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.lanik.weatherapp.core.BaseComposeFragment
import ru.lanik.weatherapp.ui.screen.WeatherScreen
import ru.lanik.weatherapp.ui.screen.WeatherViewModel

@AndroidEntryPoint
class WeatherFragment : BaseComposeFragment() {
    private val viewModel: WeatherViewModel by viewModels()

    @Composable
    override fun GetContent() {
        WeatherScreen(viewModel = viewModel, onOpenSettingsClick = { openSettings() })
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}