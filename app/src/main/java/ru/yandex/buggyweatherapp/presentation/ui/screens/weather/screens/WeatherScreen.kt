package ru.yandex.buggyweatherapp.presentation.ui.screens.weather.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yandex.buggyweatherapp.presentation.ui.screens.weather.components.WeatherCard
import ru.yandex.buggyweatherapp.presentation.ui.screens.weather.components.WeatherSearchBar
import ru.yandex.buggyweatherapp.presentation.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {

    val weatherData by viewModel.weatherData.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()

    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherSearchBar(
            value = searchText,
            onValueChange = { searchText = it },
            onSearch = {
                if (searchText.isNotBlank()) {
                    viewModel.searchWeatherByCity(searchText.trim())
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            }

            error != null -> {
                ErrorText(message = error!!)
            }

            weatherData != null -> {

                weatherData?.let { weather ->
                    WeatherCard(
                        weather = weather,
                        onFavoriteClick = { viewModel.toggleFavorite() },
                        onRefreshClick = { viewModel.fetchCurrentLocationWeather() }
                    )
                }
            }

            else -> {
                Text("Enter a city to get weather data.")
            }
        }
    }
}

@Composable
private fun ErrorText(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(8.dp)
    )
}

