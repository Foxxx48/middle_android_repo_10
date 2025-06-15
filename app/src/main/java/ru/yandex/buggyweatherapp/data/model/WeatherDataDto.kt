package ru.yandex.buggyweatherapp.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherDto(
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coord: CoordDto,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherConditionDto>,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("clouds") val clouds: CloudsDto,
    @SerializedName("sys") val sys: SysDto,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("dt") val dt: Long,
    @SerializedName("rain") val rain: PrecipitationDto? = null,
    @SerializedName("snow") val snow: PrecipitationDto? = null
)

@Keep
data class CoordDto(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)

@Keep
data class MainDto(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int
)

@Keep
data class WeatherConditionDto(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

@Keep
data class WindDto(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int? = 0
)

@Keep
data class CloudsDto(
    @SerializedName("all") val all: Int
)

@Keep
data class SysDto(
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)

@Keep
data class PrecipitationDto(
    @SerializedName("1h") val oneHour: Double?
)