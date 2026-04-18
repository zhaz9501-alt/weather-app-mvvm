package com.example.weatherapp.fake

import com.example.weatherapp.network.*

class FakeWeatherApi : WeatherApi {

    override suspend fun getWeather(
        city: String,
        key: String,
        units: String
    ): WeatherResponse {
        return WeatherResponse(
            name = city,
            main = Main(
                temp = 25.0,
                humidity = 60,
                pressure = 1013
            ),
            weather = listOf(
                Weather(
                    main = "Clear"
                )
            ),
            wind = Wind(
                speed = 3.0
            )
        )
    }

    override suspend fun getWeatherByLocation(
        lat: Double,
        lon: Double,
        apiKey: String,
        units: String
    ): WeatherResponse {
        return WeatherResponse(
            name = "Location",
            main = Main(
                temp = 28.0,
                humidity = 55,
                pressure = 1010
            ),
            weather = listOf(
                Weather(main = "Clouds")
            ),
            wind = Wind(speed = 4.0)
        )
    }
}