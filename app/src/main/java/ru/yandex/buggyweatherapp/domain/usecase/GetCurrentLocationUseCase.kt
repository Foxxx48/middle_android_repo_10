package ru.yandex.buggyweatherapp.domain.usecase

import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.repository.LocationRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor (private val repository: LocationRepository) {
    suspend operator fun invoke(): Result<LocationDataDomain> = repository.getCurrentLocation()
}