package ru.lanik.weatherapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class WeatherAppColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
) {
    val transparent = Color.Transparent
    val dark = Dark
    val light = Light
    val white = Color.White
    val blueL = LagunaL
    val blueD = LagunaD
}

val lightColorScheme =
    WeatherAppColors(
        primaryBackground = Color(0xFFFFFFFF),
        primaryText = Color(0xFF3D454C),
        secondaryBackground = Color(0xFFF3F4F5),
        secondaryText = Color(0xCC7A8A99),
        tintColor = GreenL,
        controlColor = White50,
        errorColor = RedL,
    )

val darkColorScheme =
    WeatherAppColors(
        primaryBackground = Color(0xFF23282D),
        primaryText = Color(0xFFF2F4F5),
        secondaryBackground = Color(0xFF191E23),
        secondaryText = Color(0xCC7A8A99),
        tintColor = GreenD,
        controlColor = White50,
        errorColor = RedD,
    )

val LocalWeatherAppColors =
    staticCompositionLocalOf<WeatherAppColors> {
        error("No colors provided")
    }