package com.example.weatherapp

import com.example.weatherapp.fake.FakeCityRepository
import com.example.weatherapp.fake.FakeWeatherApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class WeatherViewModelTest {

    private val repo = FakeCityRepository()
    private val api = FakeWeatherApi()

    private val viewModel = WeatherViewModel(repo, api)

    @Test
    fun addCity_shouldAddCity() = runTest {

        viewModel.addCity("Agadir")

        val result = repo.getCities().first()

        assertTrue(result.any { it.name == "Agadir" })
    }

    @Test
    fun deleteCity_shouldRemoveCity() = runTest {


        viewModel.deleteCity("Rabat")


        val result = repo.getCities().first()

        assertFalse(result.any { it.name == "Rabat" })
    }
}