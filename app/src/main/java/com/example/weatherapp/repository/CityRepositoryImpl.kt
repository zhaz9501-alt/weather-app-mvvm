package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.toDomain
import com.example.weatherapp.data.local.toEntity
import com.example.weatherapp.datal.CityDao
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CityRepositoryImpl(
    private val dao: CityDao
) : CityRepository {

    override fun getCities(): Flow<List<City>> =
        dao.getCities().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun addCity(city: City) {
        dao.insert(city.toEntity())
    }

    override suspend fun deleteCity(city: City) {
        dao.delete(city.toEntity())
    }
}