package ru.yandex.buggyweatherapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.yandex.buggyweatherapp.data.model.WeatherDto

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherDto

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): WeatherDto
}