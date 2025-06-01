package ru.yandex.buggyweatherapp.domain.usecase

import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain
import ru.yandex.buggyweatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor (
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(location: LocationDataDomain): Result<WeatherDataDomain> {
        return repository.getWeatherByLocation(location)
    }
}