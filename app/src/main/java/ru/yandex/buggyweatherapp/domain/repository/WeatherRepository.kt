package ru.yandex.buggyweatherapp.domain.repository

import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain

interface WeatherRepository {
    suspend fun getWeatherByLocation(location: LocationDataDomain): Result<WeatherDataDomain>
    suspend fun getWeatherByCity(city: String): Result<WeatherDataDomain>
}