package com.example.weatherapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherapp.Constants
import com.example.weatherapp.WeatherViewModel

@Composable
fun DetailScreen(
    city: String,
    vm: WeatherViewModel,
    navController: NavController
) {

    val data by vm.weather.collectAsState()

    LaunchedEffect(city) {
        vm.fetchWeather(city, Constants.API_KEY)
    }

    Column(Modifier.padding(16.dp)) {

        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

        data?.let {

            Text("City: ${it.name}")
            Text("Temperature: ${it.main.temp}")
            Text("Humidity: ${it.main.humidity}")
            Text("Pressure: ${it.main.pressure}")
            Text("Wind Speed: ${it.wind.speed}")

            Text(
                "State: ${
                    when (it.weather.firstOrNull()?.main?.lowercase()) {
                        "clear" -> "Sunny"
                        "clouds" -> "Cloudy"
                        "rain" -> "Rainy"
                        else -> "Unknown"
                    }
                }"
            )
        }
    }
}