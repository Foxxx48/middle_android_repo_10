package ru.yandex.buggyweatherapp.domain.usecase

import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain
import ru.yandex.buggyweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityUseCase @Inject constructor (
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<WeatherDataDomain> {
        return repository.getWeatherByCity(city)
    }
}