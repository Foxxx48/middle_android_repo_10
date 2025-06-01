package ru.yandex.buggyweatherapp.presentation.ui.screens.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain
import ru.yandex.buggyweatherapp.presentation.ui.theme.BuggyWeatherAppTheme
import ru.yandex.buggyweatherapp.utils.WeatherIconMapper

@Composable
fun WeatherCard(
    weather: WeatherDataDomain,
    onFavoriteClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = weather.cityName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row {
                    IconButton(onClick = onFavoriteClick) {
                        Icon(
                            imageVector = if (weather.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (weather.isFavorite) "Remove from favorites" else "Add to favorites"
                        )
                    }
                    IconButton(onClick = onRefreshClick) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh weather")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Temperature: %.1f°C".format(weather.temperature))
            Text("Feels like: %.1f°C".format(weather.feelsLike))
            Text("Description: ${weather.description.replaceFirstChar { it.uppercase() }}")
            Text("Humidity: ${weather.humidity}%")
            Text("Wind: %.1f m/s".format(weather.windSpeed))

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Sunrise: ${
                    WeatherIconMapper.formatTimestamp(
                    weather.sunriseTime,
                    weather.timezone
                )}")
                Text("Sunset: ${
                    WeatherIconMapper.formatTimestamp(
                    weather.sunsetTime,
                    weather.timezone
                )}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRefreshClick,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(50)
            ) {
                Text("Refresh Weather")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherAppPreview() {
    val previewWeather = WeatherDataDomain(
        cityName = "Москва",
        country = "RU",
        temperature = 21.3,
        feelsLike = 20.1,
        minTemp = 18.0,
        maxTemp = 24.5,
        humidity = 60,
        pressure = 1012,
        windSpeed = 4.5,
        windDirection = 135,
        description = "Ясно",
        icon = "01d",
        rain = null,
        snow = null,
        cloudiness = 10,
        sunriseTime = 1685604000L,  // пример: 7:00 утра
        sunsetTime = 1685658000L,   // пример: 21:00 вечера
        timezone = 10800,           // GMT+3
        timestamp = System.currentTimeMillis() / 1000, // текущее время в секундах
        rawApiData = """{"sample":"data"}""",
        isFavorite = true,
        isSelected = false
    )
    BuggyWeatherAppTheme {
        WeatherCard(
            weather = previewWeather,
            onFavoriteClick = {},
            onRefreshClick = {}
        )
    }
}