package ru.lanik.weatherapp.ui.screen

import android.Manifest
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import ru.lanik.weatherapp.R
import ru.lanik.weatherapp.core.models.CityInfo
import ru.lanik.weatherapp.core.models.CurrentWeatherData
import ru.lanik.weatherapp.core.models.DailyWeatherData
import ru.lanik.weatherapp.core.models.ScreenState
import ru.lanik.weatherapp.core.models.WeatherInfo
import ru.lanik.weatherapp.core.models.WeatherUnitsData
import ru.lanik.weatherapp.ui.helpers.CrossSlide
import ru.lanik.weatherapp.ui.helpers.LoadingAnimation
import ru.lanik.weatherapp.ui.theme.WeatherAppTheme
import java.time.LocalDate

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onOpenSettingsClick: () -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    var showPermissionNeededDialog by remember { mutableStateOf(locationPermissionState.status != PermissionStatus.Granted) }
    if (showPermissionNeededDialog) {
        PermissionNeededDialog(
            onOkClick = {
                locationPermissionState.launchPermissionRequest()
                showPermissionNeededDialog = false
            },
            onCancelClick = {
                showPermissionNeededDialog = false
            },
        )
    }

    val localPermissionStatus = (locationPermissionState.status == PermissionStatus.Granted)

    if (viewState.permissionStatus != localPermissionStatus) {
        viewModel.onPermissionStatusChange(localPermissionStatus)
    }

    Scaffold(
        containerColor = WeatherAppTheme.colors.secondaryBackground,
        modifier = Modifier.fillMaxSize(),
    ) {
        CrossSlide(
            targetState = viewState.state,
            modifier = Modifier.fillMaxSize(),
            reverseAnimation = false,
        ) { stage ->
            when (stage) {
                ScreenState.Loading -> {
                    LoadingScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    )
                }
                ScreenState.ShowForecast -> {
                    WeatherScreen(
                        cityInfo = viewState.cityInfo!!,
                        weatherInfo = viewState.weatherInfo!!,
                        onRefreshClick = { viewModel.onRefreshClick() },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    )
                }
                ScreenState.Error -> {
                    ErrorScreen(
                        errorMessage = viewState.errorMessage,
                        onRefreshClick = { viewModel.onRefreshClick() },
                        onOpenSettingsClick = { onOpenSettingsClick() },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onRefreshClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_attention_20),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = errorMessage,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp),
                textAlign = TextAlign.Center,
                style = WeatherAppTheme.typography.subhead2,
                color = WeatherAppTheme.colors.secondaryText,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Button(
                onClick = { onOpenSettingsClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WeatherAppTheme.colors.tintColor
                )
            ) {
                Text(
                    text = stringResource(id = R.string.weather_action_open_settings),
                    textAlign = TextAlign.Center,
                    style = WeatherAppTheme.typography.subhead2,
                    color = WeatherAppTheme.colors.primaryBackground,
                )
            }

            Button(
                onClick = { onRefreshClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = WeatherAppTheme.colors.tintColor
                )
            ) {
                Text(
                    text = stringResource(id = R.string.weather_action_refresh),
                    textAlign = TextAlign.Center,
                    style = WeatherAppTheme.typography.subhead2,
                    color = WeatherAppTheme.colors.primaryBackground,
                )
            }
        }
    }
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        LoadingAnimation()
    }
}

@Composable
private fun WeatherScreen(
    cityInfo: CityInfo,
    weatherInfo: WeatherInfo,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Row(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(WeatherAppTheme.shapes.generalPadding),
                horizontalArrangement = Arrangement.End,
            ) {
                SmallFloatingActionButton(
                    containerColor = WeatherAppTheme.colors.tintColor,
                    onClick = { onRefreshClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = WeatherAppTheme.colors.primaryBackground,
                    )
                }
            }
            CurrentWeatherInfo(
                cityName = cityInfo.name,
                weatherUnits = weatherInfo.weatherUnitsData,
                currentWeather = weatherInfo.currentWeatherData,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
            )
        }

        Box(
            modifier = Modifier
                .padding(PaddingValues(16.dp))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DailyWeatherInfo(
                weatherUnits = weatherInfo.weatherUnitsData,
                weatherDataPerDay = weatherInfo.weatherDataPerDay,
            )
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
            .border(
                width = 4.dp,
                WeatherAppTheme.colors.tintColor,
                WeatherAppTheme.shapes.cornersStyle
            ),
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

@Composable
private fun PermissionNeededDialog(
    onOkClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Dialog(onDismissRequest = onCancelClick) {
        Column(
            modifier =
            Modifier
                .clip(WeatherAppTheme.shapes.cornersStyle)
                .background(color = WeatherAppTheme.colors.primaryBackground)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.weather_description_dialog_label),
                color = WeatherAppTheme.colors.primaryText,
                style = WeatherAppTheme.typography.body,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.weather_description_dialog_text),
                color = WeatherAppTheme.colors.primaryText,
                style = WeatherAppTheme.typography.body,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    onClick = onCancelClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WeatherAppTheme.colors.tintColor
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.weather_description_dialog_action_no),
                        color = WeatherAppTheme.colors.primaryBackground,
                        style = WeatherAppTheme.typography.headline2,
                        textAlign = TextAlign.Center,
                    )
                }
                Button(
                    onClick = onOkClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WeatherAppTheme.colors.tintColor
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.weather_description_dialog_action_yes),
                        color = WeatherAppTheme.colors.primaryBackground,
                        style = WeatherAppTheme.typography.headline2,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScreen() {
    WeatherAppTheme {
        Scaffold(
            containerColor = WeatherAppTheme.colors.secondaryBackground,
            modifier = Modifier.fillMaxSize(),
        ) {
            CrossSlide(
                targetState = ScreenState.ShowForecast,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                reverseAnimation = false,
            ) { stage ->
                when (stage) {
                    ScreenState.Loading -> {
                        LoadingScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        )
                    }
                    ScreenState.ShowForecast -> {
                        WeatherScreen(
                            cityInfo = CityInfo("Brest", "", 0.0, 0.0),
                            weatherInfo = WeatherInfo(WeatherUnitsData(), listOf(DailyWeatherData()), CurrentWeatherData()),
                            onRefreshClick = { },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        )
                    }
                    ScreenState.Error -> {
                        ErrorScreen(
                            errorMessage = "",
                            onRefreshClick = { },
                            onOpenSettingsClick = { },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_PermissionNeededDialog() {
    WeatherAppTheme {
        PermissionNeededDialog({}, {})
    }
}
