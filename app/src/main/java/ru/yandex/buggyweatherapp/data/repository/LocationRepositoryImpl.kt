package ru.yandex.buggyweatherapp.data.repository

import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import ru.yandex.buggyweatherapp.domain.model.LocationDataDomain
import ru.yandex.buggyweatherapp.domain.repository.LocationRepository
import kotlin.coroutines.resume


class LocationRepositoryImpl(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val geocoder: Geocoder,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocationRepository {


    override suspend fun getCurrentLocation(): Result<LocationDataDomain> =
        withContext(ioDispatcher) {
            suspendCancellableCoroutine { cont ->
                try {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                val dto = LocationDataDomain(
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                                cont.resume(Result.success(dto))
                            } else {
                                cont.resume(Result.failure(Exception("Location is null")))
                            }
                        }
                        .addOnFailureListener { e ->
                            cont.resume(Result.failure(e))
                        }
                } catch (e: SecurityException) {
                    cont.resume(Result.failure(e))
                }
            }
        }

    override suspend fun getCityName(location: LocationDataDomain): Result<String> =
        withContext(ioDispatcher) {
            try {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val city = addresses?.firstOrNull()?.let { address ->
                    address.locality ?: address.subAdminArea ?: address.adminArea
                }

                city?.let { Result.success(it) } ?: Result.failure(Exception("City not found"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}
