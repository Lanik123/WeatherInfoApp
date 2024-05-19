package ru.lanik.weatherapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

data class WeatherAppShape(
    val generalPadding: Dp,
    val cornersStyle: Shape,
)

enum class WeatherAppCorners {
    Flat, Rounded,
}

val LocalWeatherAppShape = staticCompositionLocalOf<WeatherAppShape> {
    error("No shapes provided")
}