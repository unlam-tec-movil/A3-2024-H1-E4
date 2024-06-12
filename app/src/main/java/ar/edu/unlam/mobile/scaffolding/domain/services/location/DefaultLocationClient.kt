package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
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

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat
        .checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat
            .checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
}

class DefaultLocationClient
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val locationClient: FusedLocationProviderClient,
    ) : LocationClient {
        @SuppressLint("MissingPermission")
        override fun getLocationUpdates(interval: Long): Flow<Location> {
            return callbackFlow {
                if (!context.hasLocationPermission()) {
                    throw LocationClient.LocationException("Se requieren permisos de ubicación")
                }

                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkEnabled =
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!isGpsEnabled && !isNetworkEnabled) {
                    throw LocationClient.LocationException("El GPS está deshabilitado")
                }

                val request =
                    LocationRequest
                        .Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(interval))
                        .build()

                val locationCallback =
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            super.onLocationResult(result)
                            result.locations.lastOrNull()?.let { location ->
                                launch { send(location) }
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
    }
