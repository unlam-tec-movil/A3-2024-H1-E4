package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationServiceImpl : Service(), LocationService {
    private lateinit var locationClient: LocationClient

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var locationStateFlow: MutableStateFlow<Coordinate> =
        MutableStateFlow(Coordinate(0.0, 0.0))

    override fun onCreate() {
        super.onCreate()
        locationClient =
            DefaultLocationClient(
                applicationContext,
                LocationServices.getFusedLocationProviderClient(applicationContext),
            )
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun getCurrentLocation(): Coordinate = locationStateFlow.value

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        when (intent?.action) {
            ACTION_START -> startLocation()
            ACTION_STOP -> stopLocation()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    override fun startLocation() {
        val notification =
            NotificationCompat.Builder(this, "location")
                .setOngoing(true)
        /*
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         */

        val threeSeconds = 3L

        locationClient.getLocationUpdates(threeSeconds)
            .catch { exception -> exception.printStackTrace() }
            .onEach { location ->
                val latitude = location.latitude
                val longitude = location.longitude
                locationStateFlow.value.copy(latitude = latitude, longitude = longitude)
                // val updatedNotificacion = notification.setContentText( "Location coordinates: (lat=$latitude, long=$longitude)
                Log.i("CNO Location", "LAT=$latitude")
                Log.i("CNO Location", "LONG=$longitude")
                // notificationManager.notify(1, updatedNotificacion.build())
            }.launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    override fun stopLocation() {
        stopForeground(STOP_FOREGROUND_DETACH)
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}
