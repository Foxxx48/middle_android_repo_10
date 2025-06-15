package ru.yandex.buggyweatherapp.domain.model

import androidx.compose.runtime.Stable

@Stable
data class LocationDataDomain(
    val latitude: Double,
    val longitude: Double,
    val cityName: String? = null
)
