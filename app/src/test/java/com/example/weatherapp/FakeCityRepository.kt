package com.example.weatherapp.fake

import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeCityRepository : CityRepository {

    private val cities = mutableListOf(
        City("Casablanca"),
        City("Rabat"),
        City("Marrakech"),
        City("Tangier"),
        City("Fes")
    )

    private val flow = MutableStateFlow(cities)

    override fun getCities(): Flow<List<City>> = flow

    override suspend fun addCity(city: City) {
        cities.add(city)
        flow.value = cities
    }

    override suspend fun deleteCity(city: City) {
        cities.remove(city)
        flow.value = cities
    }
}