package ru.yandex.buggyweatherapp.data.model

import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain

class LocationDataMapper {
    fun mapToDomain(dto: LocationDataDto): LocationDataDomain {
        return LocationDataDomain(
            latitude = dto.latitude,
            longitude = dto.longitude,
            cityName = dto.cityName
        )
    }

    fun mapToDto(domain: LocationDataDomain): LocationDataDto {
        return LocationDataDto(
            latitude = domain.latitude,
            longitude = domain.longitude,
            cityName = domain.cityName
        )
    }
}