package com.example.weatherapp.data.local

import com.example.weatherapp.datal.CityEntity
import com.example.weatherapp.domain.City


fun CityEntity.toDomain(): City {
    return City(name)
}

fun City.toEntity(): CityEntity {
    return CityEntity(name)
}