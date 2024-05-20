package ru.lanik.weatherapp.core.models

import androidx.annotation.DrawableRes
import ru.lanik.weatherapp.R

sealed class WeatherType(
    val wmo: Int,
    @DrawableRes val iconRes: Int,
) {
    object ClearSky : WeatherType(
        wmo = 0,
        iconRes = R.drawable.ic_sunny,
    )
    object MainlyClear : WeatherType(
        wmo = 1,
        iconRes = R.drawable.ic_cloudy,
    )
    object PartlyCloudy : WeatherType(
        wmo = 2,
        iconRes = R.drawable.ic_cloudy,
    )
    object Overcast : WeatherType(
        wmo = 3,
        iconRes = R.drawable.ic_cloudy,
    )
    object Foggy : WeatherType(
        wmo = 45,
        iconRes = R.drawable.ic_very_cloudy,
    )
    object DepositingRimeFog : WeatherType(
        wmo = 48,
        iconRes = R.drawable.ic_very_cloudy,
    )
    object LightDrizzle : WeatherType(
        wmo = 51,
        iconRes = R.drawable.ic_rainshower,
    )
    object ModerateDrizzle : WeatherType(
        wmo = 53,
        iconRes = R.drawable.ic_rainshower,
    )
    object DenseDrizzle : WeatherType(
        wmo = 55,
        iconRes = R.drawable.ic_rainshower,
    )
    object LightFreezingDrizzle : WeatherType(
        wmo = 56,
        iconRes = R.drawable.ic_snowyrainy,
    )
    object DenseFreezingDrizzle : WeatherType(
        wmo = 57,
        iconRes = R.drawable.ic_snowyrainy,
    )
    object SlightRain : WeatherType(
        wmo = 61,
        iconRes = R.drawable.ic_rainy,
    )
    object ModerateRain : WeatherType(
        wmo = 63,
        iconRes = R.drawable.ic_rainy,
    )
    object HeavyRain : WeatherType(
        wmo = 65,
        iconRes = R.drawable.ic_rainy,
    )
    object HeavyFreezingRain : WeatherType(
        wmo = 67,
        iconRes = R.drawable.ic_snowyrainy,
    )
    object SlightSnowFall : WeatherType(
        wmo = 71,
        iconRes = R.drawable.ic_snowy,
    )
    object ModerateSnowFall : WeatherType(
        wmo = 73,
        iconRes = R.drawable.ic_heavysnow,
    )
    object HeavySnowFall : WeatherType(
        wmo = 75,
        iconRes = R.drawable.ic_heavysnow,
    )
    object SnowGrains : WeatherType(
        wmo = 77,
        iconRes = R.drawable.ic_heavysnow,
    )
    object SlightRainShowers : WeatherType(
        wmo = 80,
        iconRes = R.drawable.ic_rainshower,
    )
    object ModerateRainShowers : WeatherType(
        wmo = 81,
        iconRes = R.drawable.ic_rainshower,
    )
    object ViolentRainShowers : WeatherType(
        wmo = 82,
        iconRes = R.drawable.ic_rainshower,
    )
    object SlightSnowShowers : WeatherType(
        wmo = 85,
        iconRes = R.drawable.ic_snowy,
    )
    object HeavySnowShowers : WeatherType(
        wmo = 86,
        iconRes = R.drawable.ic_snowy,
    )
    object ModerateThunderstorm : WeatherType(
        wmo = 95,
        iconRes = R.drawable.ic_thunder,
    )
    object SlightHailThunderstorm : WeatherType(
        wmo = 96,
        iconRes = R.drawable.ic_rainythunder,
    )
    object HeavyHailThunderstorm : WeatherType(
        wmo = 99,
        iconRes = R.drawable.ic_rainythunder,
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when (code) {
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