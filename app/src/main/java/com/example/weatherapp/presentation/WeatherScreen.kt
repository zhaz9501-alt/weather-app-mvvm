package com.example.weatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.weatherapp.Constants
import com.example.weatherapp.WeatherViewModel
import com.google.android.gms.location.LocationServices

@Composable
fun WeatherScreen(
    vm: WeatherViewModel,
    navController: NavController
) {

    val cities by vm.cities.collectAsState(initial = emptyList())
    val weather by vm.weather.collectAsState()

    var search by remember { mutableStateOf("") }
    var newCity by remember { mutableStateOf("") }

    val context = LocalContext.current
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        if (isGranted) {
            fusedClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    vm.fetchWeatherByLocation(
                        it.latitude,
                        it.longitude,
                        Constants.API_KEY
                    )
                }
            }
        }
    }

    fun state(main: String?): String {
        return when (main?.lowercase()) {
            "clear" -> "Sunny"
            "clouds" -> "Cloudy"
            "rain", "drizzle", "thunderstorm" -> "Rainy"
            else -> "Unknown"
        }
    }

    val filteredCities = cities.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Column(Modifier.padding(16.dp)) {

        //  SEARCH
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search city") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // ADD CITY
        Row {

            OutlinedTextField(
                value = newCity,
                onValueChange = { newCity = it },
                label = { Text("Add city") },
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                if (newCity.isNotBlank()) {
                    vm.addCity(newCity)
                    newCity = ""
                }
            }) {
                Text("+")
            }
        }

        Spacer(Modifier.height(12.dp))

        //  LOCATION BUTTON
        Button(onClick = {

            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                fusedClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        vm.fetchWeatherByLocation(
                            it.latitude,
                            it.longitude,
                            Constants.API_KEY
                        )
                    }
                }

            } else {
                launcher.launch(permission)
            }

        }) {
            Text("Use My Location")
        }

        Spacer(Modifier.height(12.dp))

        // REFRESH
        Button(onClick = {
            cities.forEach {
                vm.fetchWeather(it.name, Constants.API_KEY)
            }
        }) {
            Text("Refresh Weather")
        }

        Spacer(Modifier.height(12.dp))

        //  CITY LIST
        filteredCities.forEach { city ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                onClick = {
                    vm.fetchWeather(city.name, Constants.API_KEY)
                    navController.navigate("detail/${city.name}")
                }
            ) {

                Column(Modifier.padding(12.dp)) {

                    Text(city.name)

                    weather?.takeIf { it.name == city.name }?.let {

                        Text("${it.main.temp} °C")

                        Text(state(it.weather.firstOrNull()?.main))
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        vm.deleteCity(city.name)
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}