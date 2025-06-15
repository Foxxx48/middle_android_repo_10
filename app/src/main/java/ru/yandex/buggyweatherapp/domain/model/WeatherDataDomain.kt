package ru.yandex.buggyweatherapp.domain.model

import androidx.compose.runtime.Stable

@Stable
data class WeatherDataDomain(
    val cityName: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Double,
    val windDirection: Int,
    val description: String,
    val icon: String,
    val rain: Double?,
    val snow: Double?,
    val cloudiness: Int,
    val sunriseTime: Long,
    val sunsetTime: Long,
    val timezone: Int,
    val timestamp: Long,
    val rawApiData: String = "",
    val isFavorite: Boolean = false,
    val isSelected: Boolean = false
)
