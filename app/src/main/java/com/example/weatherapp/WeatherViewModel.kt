package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.network.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: CityRepository,
    private val api: WeatherApi
) : ViewModel() {

    val cities = repo.getCities()

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather = _weather

    init {
        viewModelScope.launch {
            repo.addCity(City("Casablanca"))
            repo.addCity(City("Rabat"))
            repo.addCity(City("Marrakech"))
            repo.addCity(City("Tangier"))
            repo.addCity(City("Fes"))
        }
    }

    fun addCity(name: String) {
        viewModelScope.launch {
            repo.addCity(City(name))
        }
    }

    fun deleteCity(name: String) {
        viewModelScope.launch {
            repo.deleteCity(City(name))
        }
    }

    fun fetchWeather(city: String, key: String) {
        viewModelScope.launch {
            try {
                _weather.value = api.getWeather(city, key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun fetchWeatherByLocation(lat: Double, lon: Double, key: String) {
        viewModelScope.launch {
            try {
                _weather.value = api.getWeatherByLocation(lat, lon, key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}