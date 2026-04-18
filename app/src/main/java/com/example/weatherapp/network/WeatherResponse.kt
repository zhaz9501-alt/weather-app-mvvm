package com.example.weatherapp.network

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(val temp: Double, val humidity: Int, val pressure: Int)
data class Weather(val main: String)
data class Wind(val speed: Double)