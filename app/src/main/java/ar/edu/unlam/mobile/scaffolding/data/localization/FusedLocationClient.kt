package ar.edu.unlam.mobile.scaffolding.data.localization

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

fun Context.hasLocationPermission(): Boolean =
    ContextCompat
        .checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat
            .checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED

class FusedLocationClient
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val locationClient: FusedLocationProviderClient,
    ) : LocationClient {
        private lateinit var locationCallback: LocationCallback

        override fun getLocationUpdates(interval: Long): Flow<Coordinate> {
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
            //                if (!context.hasLocationPermission()) {
            //                    throw LocationClient.LocationException("Se requieren permisos de ubicación")
            //                }

            //                val locationManager =
            //                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            //                val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            //                val isNetworkEnabled =
            //                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            //
            //                if (!isGpsEnabled && !isNetworkEnabled) {
            //                    throw LocationClient.LocationException("El GPS está deshabilitado")
            //                }
            val request =
                LocationRequest
                    .Builder(Priority.PRIORITY_HIGH_ACCURACY, TimeUnit.SECONDS.toMillis(interval))
                    .build()
            return callbackFlow {
                locationCallback =
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            super.onLocationResult(result)
                            result.locations.lastOrNull()?.let { location ->
                                launch { send(location.toCoordinate()) }
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
