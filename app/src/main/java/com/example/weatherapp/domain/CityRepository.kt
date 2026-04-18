package com.example.weatherapp.domain

import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getCities(): Flow<List<City>>
    suspend fun addCity(city: City)
    suspend fun deleteCity(city: City)
}