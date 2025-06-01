package ru.yandex.buggyweatherapp.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.yandex.buggyweatherapp.data.model.WeatherDataMapper
import ru.yandex.buggyweatherapp.data.remote.RetrofitInstance
import ru.yandex.buggyweatherapp.data.remote.WeatherApi
import ru.yandex.buggyweatherapp.data.repository.LocationRepositoryImpl
import ru.yandex.buggyweatherapp.data.repository.WeatherRepositoryImpl
import ru.yandex.buggyweatherapp.domain.repository.LocationRepository
import ru.yandex.buggyweatherapp.domain.repository.WeatherRepository
import ru.yandex.buggyweatherapp.domain.usecase.GetCityNameUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetCurrentLocationUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetWeatherByCityUseCase
import ru.yandex.buggyweatherapp.domain.usecase.GetWeatherByLocationUseCase
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return RetrofitInstance.weatherApi
    }

    @Provides
    fun provideWeatherMapper(): WeatherDataMapper = WeatherDataMapper()

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi, mapper: WeatherDataMapper): WeatherRepository {
        return WeatherRepositoryImpl(api, mapper)
    }

    @Provides
    fun provideGetWeatherByLocationUseCase(repository: WeatherRepository): GetWeatherByLocationUseCase =
        GetWeatherByLocationUseCase(repository)

    @Provides
    fun provideGetWeatherByCityUseCase(repository: WeatherRepository): GetWeatherByCityUseCase =
        GetWeatherByCityUseCase(repository)

    @Provides
    fun provideGetCityNameUseCase(repository: LocationRepository): GetCityNameUseCase =
        GetCityNameUseCase(repository)

    @Provides
    fun provideGetCurrentLocationUseCase(repository: LocationRepository): GetCurrentLocationUseCase =
        GetCurrentLocationUseCase(repository)



    @Provides
    fun provideFusedLocationClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun provideGeocoder(
        @ApplicationContext context: Context
    ): Geocoder = Geocoder(context, Locale.getDefault())

    @Provides
    fun provideLocationRepository(
        fusedLocationClient: FusedLocationProviderClient,
        geocoder: Geocoder,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocationRepository =
        LocationRepositoryImpl(fusedLocationClient, geocoder, ioDispatcher)

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}