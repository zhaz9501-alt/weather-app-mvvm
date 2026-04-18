package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.example.weatherapp.presentation.DetailScreen
import com.example.weatherapp.presentation.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                composable("home") {
                    val vm: WeatherViewModel = hiltViewModel()
                    WeatherScreen(vm, navController)
                }

                composable("detail/{city}") { backStack ->

                    val city = backStack.arguments?.getString("city") ?: ""

                    val vm: WeatherViewModel = hiltViewModel()

                    DetailScreen(
                        city = city,
                        vm = vm,
                        navController = navController
                    )
                }
            }
        }
    }
}