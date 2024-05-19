package ru.lanik.weatherapp.core.models

import androidx.annotation.DrawableRes
import ru.lanik.weatherapp.R

sealed class WeatherType(
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        iconRes = R.drawable.ic_sunny
    )
    object MainlyClear : WeatherType(
        iconRes = R.drawable.ic_cloudy
    )
    object PartlyCloudy : WeatherType(
        iconRes = R.drawable.ic_cloudy
    )
    object Overcast : WeatherType(
        iconRes = R.drawable.ic_cloudy
    )
    object Foggy : WeatherType(
        iconRes = R.drawable.ic_very_cloudy
    )
    object DepositingRimeFog : WeatherType(
        iconRes = R.drawable.ic_very_cloudy
    )
    object LightDrizzle : WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateDrizzle : WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object DenseDrizzle : WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object LightFreezingDrizzle : WeatherType(
        iconRes = R.drawable.ic_snowyrainy
    )
    object DenseFreezingDrizzle : WeatherType(
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightRain : WeatherType(
        iconRes = R.drawable.ic_rainy
    )
    object ModerateRain : WeatherType(
        iconRes = R.drawable.ic_rainy
    )
    object HeavyRain : WeatherType(
        iconRes = R.drawable.ic_rainy
    )
    object HeavyFreezingRain: WeatherType(
        iconRes = R.drawable.ic_snowyrainy
    )
    object SlightSnowFall: WeatherType(
        iconRes = R.drawable.ic_snowy
    )
    object ModerateSnowFall: WeatherType(
        iconRes = R.drawable.ic_heavysnow
    )
    object HeavySnowFall: WeatherType(
        iconRes = R.drawable.ic_heavysnow
    )
    object SnowGrains: WeatherType(
        iconRes = R.drawable.ic_heavysnow
    )
    object SlightRainShowers: WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object ModerateRainShowers: WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object ViolentRainShowers: WeatherType(
        iconRes = R.drawable.ic_rainshower
    )
    object SlightSnowShowers: WeatherType(
        iconRes = R.drawable.ic_snowy
    )
    object HeavySnowShowers: WeatherType(
        iconRes = R.drawable.ic_snowy
    )
    object ModerateThunderstorm: WeatherType(
        iconRes = R.drawable.ic_thunder
    )
    object SlightHailThunderstorm: WeatherType(
        iconRes = R.drawable.ic_rainythunder
    )
    object HeavyHailThunderstorm: WeatherType(
        iconRes = R.drawable.ic_rainythunder
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}