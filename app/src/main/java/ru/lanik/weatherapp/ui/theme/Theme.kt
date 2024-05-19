package ru.lanik.weatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

enum class WeatherAppSize {
    Small,
    Medium,
    Big,
}

@Composable
fun WeatherAppTheme(
    paddingSize: WeatherAppSize = WeatherAppSize.Medium,
    corners: WeatherAppCorners = WeatherAppCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            darkTheme -> darkColorScheme
            else -> lightColorScheme
        }

    val typography =
        WeatherAppTypography()

    val shapes =
        WeatherAppShape(
            generalPadding =
            when (paddingSize) {
                WeatherAppSize.Small -> 12.dp
                WeatherAppSize.Medium -> 16.dp
                WeatherAppSize.Big -> 20.dp
            },
            cornersStyle =
            when (corners) {
                WeatherAppCorners.Flat -> RoundedCornerShape(0.dp)
                WeatherAppCorners.Rounded -> RoundedCornerShape(8.dp)
            },
        )
    CompositionLocalProvider(
        LocalWeatherAppColors provides colorScheme,
        LocalWeatherAppTypography provides typography,
        LocalWeatherAppShape provides shapes,
        content = content,
    )
}

object WeatherAppTheme {
    val colors: WeatherAppColors
        @Composable
        get() = LocalWeatherAppColors.current

    val typography: WeatherAppTypography
        @Composable
        get() = LocalWeatherAppTypography.current

    val shapes: WeatherAppShape
        @Composable
        get() = LocalWeatherAppShape.current
}