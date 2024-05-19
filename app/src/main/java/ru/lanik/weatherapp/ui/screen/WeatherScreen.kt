package ru.lanik.weatherapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import ru.lanik.weatherapp.R
import ru.lanik.weatherapp.core.models.CurrentWeatherData
import ru.lanik.weatherapp.core.models.DailyWeatherData
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.models.WeatherType
import ru.lanik.weatherapp.core.models.WeatherUnitsData
import ru.lanik.weatherapp.ui.theme.WeatherAppTheme
import ru.lanik.weatherapp.ui.theme.White50
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
) {
    val viewState by viewModel.viewState.collectAsState()
    Scaffold(
        containerColor = WeatherAppTheme.colors.secondaryBackground,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (viewState.isLoading) {
                Text("Loading", color = Color.White)
            } else if (viewState.weatherInfo != null && viewState.cityInfo != null) {
                CurrentWeatherInfo(
                    cityName = viewState.cityInfo!!.name,
                    weatherUnits = viewState.weatherInfo!!.weatherUnitsData,
                    currentWeather = viewState.weatherInfo!!.currentWeatherData,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                )
                Box(
                    modifier = Modifier
                        .padding(PaddingValues(16.dp))
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DailyWeatherInfo(
                        weatherUnits = viewState.weatherInfo!!.weatherUnitsData,
                        weatherDataPerDay = viewState.weatherInfo!!.weatherDataPerDay,
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrentWeatherInfo(
    cityName: String,
    weatherUnits: WeatherUnitsData,
    currentWeather: CurrentWeatherData,
    modifier: Modifier = Modifier,
) {
    val temp = "${currentWeather.temperature.toInt()} ${weatherUnits.temperatureUnits}"
    val apTemp = "${currentWeather.apTemperature.toInt()} ${weatherUnits.temperatureUnits}"
    val humidity = "${currentWeather.humidity.toInt()} ${weatherUnits.humidityUnits}"
    val pressure = "${currentWeather.pressure.toInt()} ${weatherUnits.pressureUnits}"
    val windSpeed = "${currentWeather.windSpeed.toInt()} ${weatherUnits.windSpeedUnits}"

    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.weather_description_currentlocation),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = WeatherAppTheme.typography.title3,
            color = WeatherAppTheme.colors.primaryText,
        )
        Text(
            text = cityName,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = WeatherAppTheme.typography.title3,
            color = WeatherAppTheme.colors.primaryText,
        )
        Text(
            text = temp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = WeatherAppTheme.typography.title1,
            color = WeatherAppTheme.colors.primaryText,
        )
        Box(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()) {
            Image(
                painter = painterResource(id = currentWeather.weatherType.iconRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(84.dp),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.TopStart),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = stringResource(R.string.weather_description_humidity),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.body,
                        color = WeatherAppTheme.colors.primaryText,
                    )

                    Text(
                        text = humidity,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.title3,
                        color = WeatherAppTheme.colors.primaryText,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.TopEnd),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.weather_description_wind),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.body,
                        color = WeatherAppTheme.colors.primaryText,
                    )

                    Text(
                        text = windSpeed,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.title3,
                        color = WeatherAppTheme.colors.primaryText,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomStart),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.weather_description_pressure),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.body,
                        color = WeatherAppTheme.colors.primaryText,
                    )

                    Text(
                        text = pressure,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.title3,
                        color = WeatherAppTheme.colors.primaryText,
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .padding(start = 32.dp)
                    .fillMaxWidth(fraction = 0.25f)
                    .height(1.dp)
                    .align(Alignment.CenterStart)
                    .background(WeatherAppTheme.colors.controlColor),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomEnd),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.weather_description_realfeel),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.body,
                        color = WeatherAppTheme.colors.primaryText,
                    )

                    Text(
                        text = apTemp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = WeatherAppTheme.typography.title3,
                        color = WeatherAppTheme.colors.primaryText,
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .padding(end = 32.dp)
                    .fillMaxWidth(fraction = 0.25f)
                    .height(1.dp)
                    .align(Alignment.CenterEnd)
                    .background(WeatherAppTheme.colors.controlColor),
            )
        }
    }
}

@Composable
private fun DailyWeatherInfo(
    weatherUnits: WeatherUnitsData,
    weatherDataPerDay: List<DailyWeatherData>,
    modifier: Modifier = Modifier,
) {
    val data = weatherDataPerDay.take(10)
    LazyColumn(
        modifier = modifier
            .background(WeatherAppTheme.colors.primaryBackground)
            .clip(WeatherAppTheme.shapes.cornersStyle)
            .border(width = 4.dp, WeatherAppTheme.colors.tintColor, WeatherAppTheme.shapes.cornersStyle),
        contentPadding = PaddingValues(WeatherAppTheme.shapes.generalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(data) {
            val temp = "${it.temperatureMax.toInt()} ${weatherUnits.temperatureUnits}"
            val dayName = if(it.time == LocalDate.now()) "TODAY" else it.time.dayOfWeek.name.substring(0, 3)
            DailyWeatherItem(
                dataName = dayName,
                icon = painterResource(id = it.weatherType.iconRes),
                formatTemp = temp,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = WeatherAppTheme.colors.tintColor
            )
        }
    }
}

@Composable
private fun DailyWeatherItem(
    dataName: String,
    icon: Painter,
    formatTemp: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clip(WeatherAppTheme.shapes.cornersStyle),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = dataName,
            style = WeatherAppTheme.typography.title3,
            color = WeatherAppTheme.colors.primaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f),
        )

        Image(
            painter = icon,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(25.dp)
                .weight(1f),
        )

        Text(
            text = formatTemp,
            style = WeatherAppTheme.typography.title2,
            color = WeatherAppTheme.colors.primaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f),
        )
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    WeatherAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CurrentWeatherInfo(
                    cityName = "Brest",
                    modifier = Modifier
                        .fillMaxWidth(),
                    weatherUnits = WeatherUnitsData(),
                    currentWeather = CurrentWeatherData()
                )
                Box(
                    modifier = Modifier
                        .padding(PaddingValues(16.dp))
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DailyWeatherInfo(
                        weatherUnits = WeatherUnitsData(),
                        weatherDataPerDay = listOf(DailyWeatherData()),
                        modifier = Modifier.fillMaxWidth(fraction = 0.9f)
                    )
                }
            }
        }
    }
}
