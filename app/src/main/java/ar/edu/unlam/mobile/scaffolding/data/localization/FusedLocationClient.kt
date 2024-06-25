package ar.edu.unlam.mobile.scaffolding.data.localization

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.models.location.toCoordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FusedLocationClient
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val locationClient: FusedLocationProviderClient,
    ) : LocationClient {
        private lateinit var locationCallback: LocationCallback

        override fun getLocationUpdates(interval: Long): Flow<Coordinate> {
            var distanceTracking = 0.0f
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw LocationClient.LocationException("Se requieren permisos de ubicación")
            }

            val request =
                LocationRequest
                    .Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(interval))
                    .build()

            return callbackFlow {
                locationCallback =
                    object : LocationCallback() {
                        private var lastLocation: Location? = null

                        override fun onLocationResult(result: LocationResult) {
                            super.onLocationResult(result)
                            result.locations.lastOrNull()?.let { location ->
                                if (lastLocation != null) {
                                    val distance = lastLocation!!.distanceTo(location) / 1000
                                    distanceTracking += distance
                                }
                                lastLocation = location
                                launch { send(location.toCoordinate(distanceTracking)) }
                            }
                        }
                    }
                locationClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper(),
                )

                awaitClose {
                    locationClient.removeLocationUpdates(locationCallback)
                }
            }
        }

        override fun stopLocationUpdates() {
            locationClient.removeLocationUpdates(locationCallback)
        }
    }
