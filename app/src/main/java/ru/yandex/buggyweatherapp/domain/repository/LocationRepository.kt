package ru.yandex.buggyweatherapp.domain.repository

import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain

interface LocationRepository {

    suspend fun getCurrentLocation(): Result<LocationDataDomain>
    suspend fun getCityName(location: LocationDataDomain): Result<String>
//    suspend fun getCurrentLocation(): AppLocationDto?
//
//    fun startLocationTracking(callback: (AppLocationDto) -> Unit)
//
//    fun stopLocationTracking()
//
//    suspend fun getCityNameFromLocation(location: AppLocationDto): String?


}