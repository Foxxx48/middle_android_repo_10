package ru.yandex.buggyweatherapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.model.WeatherDataDomain
import ru.yandex.buggyweatherapp.domain.usecase.GetCityNameUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetCurrentLocationUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetWeatherByCityUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetWeatherByLocationUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val getWeatherByCityUseCase: GetWeatherByCityUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getCityNameUseCase: GetCityNameUseCase
) : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherDataDomain>()
    val weatherData: LiveData<WeatherDataDomain> = _weatherData

    private val _location = MutableLiveData<LocationDataDomain>()
    val location: LiveData<LocationDataDomain> = _location

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> = _cityName

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private var refreshJob: Job? = null

    init {
        fetchCurrentLocationWeather()
        startAutoRefresh()
    }

 fun fetchCurrentLocationWeather() {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            val locationResult = getCurrentLocationUseCase()
            locationResult.onSuccess { loc ->
                _location.value = loc

                val cityResult = getCityNameUseCase(loc)
                cityResult.onSuccess { city -> _cityName.value = city }

                val weatherResult = getWeatherByLocationUseCase(loc)
                weatherResult
                    .onSuccess { _weatherData.value = it }
                    .onFailure { _error.value = it.message }

            }.onFailure {
                _error.value = it.message ?: "Failed to get location"
            }

            _isLoading.value = false
        }
    }

    fun searchWeatherByCity(city: String) {
        if (city.isBlank()) {
            _error.value = "City name cannot be empty"
            return
        }

        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            val result = getWeatherByCityUseCase(city)
            result
                .onSuccess {
                    _weatherData.value = it
                    _cityName.value = it.cityName
                    _location.value = LocationDataDomain(0.0, 0.0, it.cityName)
                }
                .onFailure { _error.value = it.message }

            _isLoading.value = false
        }
    }

    private fun startAutoRefresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            while (isActive) {
                delay(60_000)
                location.value?.let { location ->
                    val result = getWeatherByLocationUseCase(location)
                    result.onSuccess { _weatherData.value = it }
                }
            }
        }
    }

//TODO
//    fun loadWeatherIcon(iconCode: String) {
//        viewModelScope.launch {
//            val iconUrl = "https://openweathermap.org/img/wn/$iconCode@2x.png"
//            ImageLoader.loadImage(iconUrl)
//        }
//    }

    fun toggleFavorite() {
        weatherData.value?.let { current ->
            val updated = current.copy(isFavorite = !current.isFavorite)
            _weatherData.value = updated
        }
    }

    override fun onCleared() {
        super.onCleared()
        refreshJob?.cancel()
    }
}
