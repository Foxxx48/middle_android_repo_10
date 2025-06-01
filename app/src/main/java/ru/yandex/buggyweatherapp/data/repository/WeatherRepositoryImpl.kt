package ru.yandex.buggyweatherapp.data.repository

import ru.yandex.buggyweatherapp.data.model.WeatherDataMapper
import ru.yandex.buggyweatherapp.data.remote.WeatherApi
import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain
import ru.yandex.buggyweatherapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val mapper: WeatherDataMapper
) : WeatherRepository {

    override suspend fun getWeatherByLocation(location: LocationDataDomain): Result<WeatherDataDomain> =
        runCatching {
            val dto = api.getCurrentWeather(location.latitude, location.longitude)
            mapper.map(dto)
        }

    override suspend fun getWeatherByCity(city: String): Result<WeatherDataDomain> =
        runCatching {
            val dto = api.getWeatherByCity(city)
            mapper.map(dto)
        }
}