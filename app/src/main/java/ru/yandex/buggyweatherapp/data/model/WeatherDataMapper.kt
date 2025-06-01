package ru.yandex.buggyweatherapp.data.model

import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain

class WeatherDataMapper {
    fun map(dto: WeatherDto): WeatherDataDomain {
        val weather = dto.weather.firstOrNull()

        return WeatherDataDomain(
            cityName = dto.name,
            country = dto.sys.country,
            temperature = dto.main.temp,
            feelsLike = dto.main.feelsLike,
            minTemp = dto.main.tempMin,
            maxTemp = dto.main.tempMax,
            humidity = dto.main.humidity,
            pressure = dto.main.pressure,
            windSpeed = dto.wind.speed,
            windDirection = dto.wind.deg ?: 0,
            description = weather?.description ?: "",
            icon = weather?.icon ?: "",
            rain = dto.rain?.oneHour,
            snow = dto.snow?.oneHour,
            cloudiness = dto.clouds.all,
            sunriseTime = dto.sys.sunrise,
            sunsetTime = dto.sys.sunset,
            timezone = dto.timezone,
            timestamp = dto.dt,
            rawApiData = dto.toString()
        )
    }
}