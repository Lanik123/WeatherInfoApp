package ru.lanik.weatherapp.modules.weather

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
    @Inject
    constructor() : ViewModel()